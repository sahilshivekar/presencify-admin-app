package edu.watumull.presencify.core.data.network.teacher

import edu.watumull.presencify.core.data.dto.teacher.TeacherDto
import edu.watumull.presencify.core.data.dto.teacher.TeacherListWithTotalCountDto
import edu.watumull.presencify.core.data.dto.teacher.TeacherTeachesCourseDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.TeacherRole

interface RemoteTeacherDataSource {
    suspend fun getTeachers(
        searchQuery: String? = null,
        courseId: String? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null,
    ): Result<TeacherListWithTotalCountDto, DataError.Remote>

    suspend fun addTeacher(
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
    ): Result<TeacherDto, DataError.Remote>

    suspend fun updateTeacherDetails(
        id: String,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        role: TeacherRole?,
        gender: Gender?,
        highestQualification: String?,
        phoneNumber: String?,
    ): Result<TeacherDto, DataError.Remote>

    suspend fun updateTeacherPassword(
        id: String,
        password: String,
        confirmPassword: String,
    ): Result<Unit, DataError.Remote>

    suspend fun updateTeacherImage(
        id: String,
        imageBytes: ByteArray?,
    ): Result<TeacherDto, DataError.Remote>

    suspend fun removeTeacherImage(id: String): Result<TeacherDto, DataError.Remote>

    suspend fun removeTeacher(id: String): Result<Unit, DataError.Remote>

    suspend fun getTeachingSubjects(teacherId: String): Result<List<TeacherTeachesCourseDto>, DataError.Remote>

    suspend fun addTeachingSubject(
        teacherId: String,
        courseId: String,
    ): Result<TeacherTeachesCourseDto, DataError.Remote>

    suspend fun removeTeachingSubject(teacherSubjectId: String): Result<Unit, DataError.Remote>

    suspend fun getTeacherById(id: String): Result<TeacherDto, DataError.Remote>

    suspend fun bulkCreateTeachers(teachers: List<Map<String, Any>>): Result<List<TeacherDto>, DataError.Remote>

    suspend fun bulkDeleteTeachers(teacherIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkCreateTeachersFromCSV(csvData: ByteArray): Result<List<TeacherDto>, DataError.Remote>
}
