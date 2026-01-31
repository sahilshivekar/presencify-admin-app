package edu.watumull.presencify.core.data.repository.teacher

import edu.watumull.presencify.core.data.mapper.teacher.toDomain
import edu.watumull.presencify.core.data.network.teacher.RemoteTeacherDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.TeacherRole
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.teacher.Teacher
import edu.watumull.presencify.core.domain.model.teacher.TeacherListWithTotalCount
import edu.watumull.presencify.core.domain.model.teacher.TeacherTeachesCourse
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository

class TeacherRepositoryImpl(
    private val remoteDataSource: RemoteTeacherDataSource
) : TeacherRepository {

    override suspend fun getTeachers(
        searchQuery: String?,
        courseId: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<TeacherListWithTotalCount, DataError.Remote> {
        return remoteDataSource.getTeachers(searchQuery, courseId, page, limit, getAll).map { teacherListWithTotalCountDto ->
            teacherListWithTotalCountDto.toDomain()
        }
    }

    override suspend fun addTeacher(
        firstName: String,
        middleName: String?,
        lastName: String,
        email: String,
        phoneNumber: String,
        gender: Gender,
        highestQualification: String?,
        role: TeacherRole,
        isActive: Boolean?,
        teacherImage: ByteArray?
    ): Result<Teacher, DataError.Remote> {
        return remoteDataSource.addTeacher(
            firstName,
            middleName,
            lastName,
            email,
            phoneNumber,
            gender,
            highestQualification,
            role,
            isActive,
            teacherImage = teacherImage
        ).map { it.toDomain() }
    }

    override suspend fun getTeacherById(id: String): Result<Teacher, DataError.Remote> {
        return remoteDataSource.getTeacherById(id).map { it.toDomain() }
    }

    override suspend fun updateTeacherDetails(
        id: String,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        role: TeacherRole?,
        gender: Gender?,
        highestQualification: String?,
        phoneNumber: String?,
    ): Result<Teacher, DataError.Remote> {
        return remoteDataSource.updateTeacherDetails(
            id,
            firstName,
            middleName,
            lastName,
            email,
            role,
            gender,
            highestQualification,
            phoneNumber,
        ).map { it.toDomain() }
    }

    override suspend fun updateTeacherPassword(id: String, password: String, confirmPassword: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.updateTeacherPassword(id, password, confirmPassword)
    }

    override suspend fun updateTeacherImage(id: String, teacherImage: ByteArray?): Result<Teacher, DataError.Remote> {
        return remoteDataSource.updateTeacherImage(id, teacherImage).map { it.toDomain() }
    }

    override suspend fun removeTeacherImage(id: String): Result<Teacher, DataError.Remote> {
        return remoteDataSource.removeTeacherImage(id).map { it.toDomain() }
    }

    override suspend fun removeTeacher(id: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeTeacher(id)
    }

    override suspend fun getTeachingSubjects(teacherId: String): Result<List<TeacherTeachesCourse>, DataError.Remote> {
        return remoteDataSource.getTeachingSubjects(teacherId).map { subjects ->
            subjects.map { it.toDomain() }
        }
    }

    override suspend fun addTeachingSubject(
        teacherId: String,
        courseId: String
    ): Result<TeacherTeachesCourse, DataError.Remote> {
        return remoteDataSource.addTeachingSubject(teacherId, courseId).map { it.toDomain() }
    }

    override suspend fun removeTeachingSubject(teacherSubjectId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeTeachingSubject(teacherSubjectId)
    }

    override suspend fun bulkCreateTeachers(teachers: List<Map<String, Any>>): Result<List<Teacher>, DataError.Remote> {
        return remoteDataSource.bulkCreateTeachers(teachers).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkDeleteTeachers(teacherIds: List<String>): Result<Unit, DataError.Remote> {
        return remoteDataSource.bulkDeleteTeachers(teacherIds)
    }

    override suspend fun bulkCreateTeachersFromCSV(csvData: ByteArray): Result<List<Teacher>, DataError.Remote> {
        return remoteDataSource.bulkCreateTeachersFromCSV(csvData).map { list ->
            list.map { it.toDomain() }
        }
    }
}
