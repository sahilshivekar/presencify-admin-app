package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.CourseDto
import edu.watumull.presencify.core.data.dto.academics.CourseListWithTotalCountDto
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.ADD_COURSE
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.BULK_CREATE_COURSES
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.BULK_CREATE_COURSES_FROM_CSV
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.BULK_DELETE_COURSES
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_COURSES
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_COURSE_BY_ID
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.REMOVE_COURSE
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.UPDATE_COURSE
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteCourseDataSource(
    private val httpClient: HttpClient
) : RemoteCourseDataSource {

    override suspend fun getCourses(
        searchQuery: String?,
        branchId: String?,
        semesterNumber: SemesterNumber?,
        schemeId: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<CourseListWithTotalCountDto, DataError.Remote> {
        return safeCall<CourseListWithTotalCountDto> {
            httpClient.get(GET_COURSES) {
                searchQuery?.let { parameter("searchQuery", it) }
                branchId?.let { parameter("branchId", it) }
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                schemeId?.let { parameter("schemeId", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun addCourse(
        code: String,
        name: String,
        optionalSubject: String?,
        schemeId: String
    ): Result<CourseDto, DataError.Remote> {
        return safeCall<CourseDto> {
            httpClient.post(ADD_COURSE) {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    put("code", code)
                    put("name", name)
                    optionalSubject?.let { put("optionalSubject", it) }
                    put("schemeId", schemeId)
                })
            }
        }
    }

    override suspend fun getCourseById(id: String): Result<CourseDto, DataError.Remote> {
        return safeCall<CourseDto> {
            httpClient.get("$GET_COURSE_BY_ID/$id")
        }
    }

    override suspend fun updateCourse(
        id: String,
        code: String?,
        name: String?,
        optionalSubject: String?,
        schemeId: String?
    ): Result<CourseDto, DataError.Remote> {
        return safeCall<CourseDto> {
            httpClient.put("$UPDATE_COURSE/$id") {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    code?.let { put("code", it) }
                    name?.let { put("name", it) }
                    optionalSubject?.let { put("optionalSubject", it) }
                    schemeId?.let { put("schemeId", it) }
                })
            }
        }
    }

    override suspend fun removeCourse(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_COURSE/$id")
        }
    }

    override suspend fun addCourseToBranchWithSemesterNumber(
        courseId: String,
        branchId: String,
        semesterNumber: SemesterNumber,
    ): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post("$PRESENCIFY_BASE_URL/$API_V1/courses/branch") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("courseId" to courseId))
            }
        }
    }

    override suspend fun removeCourseFromBranchWithSemesterNumber(
        branchCourseSemesterId: String,
    ): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$PRESENCIFY_BASE_URL/$API_V1/courses/branch/$branchCourseSemesterId")
        }
    }

    override suspend fun bulkCreateCourses(courses: List<Map<String, Any>>): Result<List<CourseDto>, DataError.Remote> {
        return safeCall<List<CourseDto>> {
            httpClient.post(BULK_CREATE_COURSES) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("courses" to courses))
            }
        }
    }

    override suspend fun bulkDeleteCourses(courseIds: List<String>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(BULK_DELETE_COURSES) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("courseIds" to courseIds))
            }
        }
    }

    override suspend fun bulkCreateCoursesFromCSV(csvData: String): Result<List<CourseDto>, DataError.Remote> {
        return safeCall<List<CourseDto>> {
            httpClient.post(BULK_CREATE_COURSES_FROM_CSV) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("csvData" to csvData))
            }
        }
    }
}