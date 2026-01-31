package edu.watumull.presencify.core.domain.repository.teacher

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.TeacherRole
import edu.watumull.presencify.core.domain.model.teacher.Teacher
import edu.watumull.presencify.core.domain.model.teacher.TeacherListWithTotalCount
import edu.watumull.presencify.core.domain.model.teacher.TeacherTeachesCourse

interface TeacherRepository {
    suspend fun getTeachers(
        searchQuery: String? = null,
        courseId: String? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null,
    ): Result<TeacherListWithTotalCount, DataError.Remote>

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
        teacherImage: ByteArray?,
    ): Result<Teacher, DataError.Remote>

    suspend fun getTeacherById(id: String): Result<Teacher, DataError.Remote>

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
    ): Result<Teacher, DataError.Remote>

    suspend fun updateTeacherPassword(
        id: String,
        password: String,
        confirmPassword: String,
    ): Result<Unit, DataError.Remote>

    suspend fun updateTeacherImage(
        id: String,
        teacherImage: ByteArray?,
    ): Result<Teacher, DataError.Remote>

    suspend fun removeTeacherImage(id: String): Result<Teacher, DataError.Remote>

    suspend fun removeTeacher(id: String): Result<Unit, DataError.Remote>

    // Teaching subjects operations
    suspend fun getTeachingSubjects(teacherId: String): Result<List<TeacherTeachesCourse>, DataError.Remote>

    suspend fun addTeachingSubject(
        teacherId: String,
        courseId: String,
    ): Result<TeacherTeachesCourse, DataError.Remote>

    suspend fun removeTeachingSubject(teacherSubjectId: String): Result<Unit, DataError.Remote>

    // Bulk operations
    suspend fun bulkCreateTeachers(teachers: List<Map<String, Any>>): Result<List<Teacher>, DataError.Remote>

    suspend fun bulkDeleteTeachers(teacherIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkCreateTeachersFromCSV(csvData: ByteArray): Result<List<Teacher>, DataError.Remote>
}
