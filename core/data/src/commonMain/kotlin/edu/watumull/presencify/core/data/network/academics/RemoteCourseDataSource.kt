package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.CourseDto
import edu.watumull.presencify.core.data.dto.academics.CourseListWithTotalCountDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber

interface RemoteCourseDataSource {
    suspend fun getCourses(
        searchQuery: String? = null,
        branchId: String? = null,
        semesterNumber: SemesterNumber? = null,
        schemeId: String? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null,
    ): Result<CourseListWithTotalCountDto, DataError.Remote>

    suspend fun addCourse(
        code: String,
        name: String,
        optionalSubject: String?,
        schemeId: String,
    ): Result<CourseDto, DataError.Remote>

    suspend fun getCourseById(id: String): Result<CourseDto, DataError.Remote>

    suspend fun updateCourse(
        id: String,
        code: String?,
        name: String?,
        optionalSubject: String?,
        schemeId: String?,
    ): Result<CourseDto, DataError.Remote>

    suspend fun addCourseToBranchWithSemesterNumber(
        courseId: String,
        branchId: String,
        semesterNumber: SemesterNumber,
    ): Result<Unit, DataError.Remote>

    suspend fun removeCourseFromBranchWithSemesterNumber(branchCourseSemesterId: String): Result<Unit, DataError.Remote>

    suspend fun bulkCreateCourses(courses: List<Map<String, Any>>): Result<List<CourseDto>, DataError.Remote>

    suspend fun bulkDeleteCourses(courseIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkCreateCoursesFromCSV(csvData: String): Result<List<CourseDto>, DataError.Remote>

    suspend fun removeCourse(id: String): Result<Unit, DataError.Remote>
}