package edu.watumull.presencify.core.domain.repository.academics

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.academics.CourseListWithTotalCount

interface CourseRepository {
    suspend fun getCourses(
        searchQuery: String? = null,
        branchId: String? = null,
        semesterNumber: SemesterNumber? = null,
        schemeId: String? = null,
        onlyOptional: Boolean = false,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null,
    ): Result<CourseListWithTotalCount, DataError.Remote>

    suspend fun addCourse(
        code: String,
        name: String,
        optionalCourse: String?,
        schemeId: String
    ): Result<Course, DataError.Remote>

    suspend fun getCourseById(id: String): Result<Course, DataError.Remote>

    suspend fun updateCourse(
        id: String,
        code: String? = null,
        name: String? = null,
        optionalCourse: String? = null,
        schemeId: String? = null
    ): Result<Course, DataError.Remote>

    suspend fun removeCourse(id: String): Result<Unit, DataError.Remote>

    suspend fun addCourseToBranchWithSemesterNumber(
        courseId: String,
        branchId: String,
        semesterNumber: SemesterNumber
    ): Result<Unit, DataError.Remote>

    suspend fun removeCourseFromBranchWithSemesterNumber(branchCourseSemesterId: String): Result<Unit, DataError.Remote>

    suspend fun bulkCreateCourses(courses: List<Map<String, Any>>): Result<List<Course>, DataError.Remote>

    suspend fun bulkDeleteCourses(courseIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkCreateCoursesFromCSV(csvData: String): Result<List<Course>, DataError.Remote>
}