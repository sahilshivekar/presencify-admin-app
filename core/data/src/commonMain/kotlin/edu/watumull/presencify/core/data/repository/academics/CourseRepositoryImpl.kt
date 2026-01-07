package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteCourseDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository

class CourseRepositoryImpl(
    private val remoteCourseDataSource: RemoteCourseDataSource
) : CourseRepository {

    override suspend fun getCourses(
        searchQuery: String?,
        branchId: String?,
        semesterNumber: SemesterNumber?,
        schemeId: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<List<Course>, DataError.Remote> {
        return remoteCourseDataSource.getCourses(
            searchQuery, branchId, semesterNumber, schemeId, page, limit, getAll
        ).map { it.courses.map { course -> course.toDomain() } }
    }

    override suspend fun addCourse(
        code: String,
        name: String,
        optionalSubject: String?,
        schemeId: String
    ): Result<Course, DataError.Remote> {
        return remoteCourseDataSource.addCourse(code, name, optionalSubject, schemeId).map { it.toDomain() }
    }

    override suspend fun getCourseById(id: String): Result<Course, DataError.Remote> {
        return remoteCourseDataSource.getCourseById(id).map { it.toDomain() }
    }

    override suspend fun updateCourse(
        id: String,
        code: String?,
        name: String?,
        optionalSubject: String?,
        schemeId: String?
    ): Result<Course, DataError.Remote> {
        return remoteCourseDataSource.updateCourse(id, code, name, optionalSubject, schemeId).map { it.toDomain() }
    }

    override suspend fun removeCourse(id: String): Result<Unit, DataError.Remote> {
        return remoteCourseDataSource.removeCourse(id)
    }

    override suspend fun addCourseToBranchWithSemesterNumber(
        courseId: String,
        branchId: String,
        semesterNumber: SemesterNumber
    ): Result<Unit, DataError.Remote> {
        return remoteCourseDataSource.addCourseToBranchWithSemesterNumber(courseId, branchId, semesterNumber)
    }

    override suspend fun removeCourseFromBranchWithSemesterNumber(branchCourseSemesterId: String): Result<Unit, DataError.Remote> {
        return remoteCourseDataSource.removeCourseFromBranchWithSemesterNumber(branchCourseSemesterId)
    }

    override suspend fun bulkCreateCourses(courses: List<Map<String, Any>>): Result<List<Course>, DataError.Remote> {
        return remoteCourseDataSource.bulkCreateCourses(courses).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkDeleteCourses(courseIds: List<String>): Result<Unit, DataError.Remote> {
        return remoteCourseDataSource.bulkDeleteCourses(courseIds)
    }

    override suspend fun bulkCreateCoursesFromCSV(csvData: String): Result<List<Course>, DataError.Remote> {
        return remoteCourseDataSource.bulkCreateCoursesFromCSV(csvData).map { list ->
            list.map { it.toDomain() }
        }
    }


}