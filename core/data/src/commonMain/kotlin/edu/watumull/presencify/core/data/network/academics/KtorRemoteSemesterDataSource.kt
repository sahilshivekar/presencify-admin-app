package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.SemesterDto
import edu.watumull.presencify.core.data.dto.academics.SemesterListWithTotalCountDto
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.ADD_SEMESTER
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.BULK_CREATE_SEMESTERS
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.BULK_DELETE_SEMESTERS
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_COURSES_OF_SEMESTER
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_SEMESTERS
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_SEMESTER_BY_ID
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.REMOVE_SEMESTER
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.UPDATE_SEMESTER
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
import kotlinx.datetime.LocalDate

class KtorRemoteSemesterDataSource(
    private val httpClient: HttpClient
) : RemoteSemesterDataSource {

    override suspend fun getSemesters(
        semesterNumber: SemesterNumber?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        branchId: String?,
        schemeId: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<SemesterListWithTotalCountDto, DataError.Remote> {
        return safeCall<SemesterListWithTotalCountDto> {
            httpClient.get(GET_SEMESTERS) {
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                academicStartYear?.let { parameter("academicStartYear", it) }
                academicEndYear?.let { parameter("academicEndYear", it) }
                branchId?.let { parameter("branchId", it) }
                schemeId?.let { parameter("schemeId", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun addSemester(
        branchId: String,
        semesterNumber: SemesterNumber,
        academicStartYear: Int,
        academicEndYear: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        schemeId: String,
        optionalCourseIds: List<String>?
    ): Result<SemesterDto, DataError.Remote> {
        return safeCall<SemesterDto> {
            httpClient.post(ADD_SEMESTER) {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    put("branchId", branchId)
                    put("semesterNumber", semesterNumber.value)
                    put("academicStartYear", academicStartYear)
                    put("academicEndYear", academicEndYear)
                    put("startDate", startDate.toString())
                    put("endDate", endDate.toString())
                    put("schemeId", schemeId)
                    optionalCourseIds?.let { put("optionalCourseIds", it) }
                })
            }
        }
    }

    override suspend fun getSemesterById(id: String): Result<SemesterDto, DataError.Remote> {
        return safeCall<SemesterDto> {
            httpClient.get("$GET_SEMESTER_BY_ID/$id")
        }
    }

    override suspend fun updateSemester(
        id: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<SemesterDto, DataError.Remote> {
        return safeCall<SemesterDto> {
            httpClient.put("$UPDATE_SEMESTER/$id") {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "startDate" to startDate.toString(),
                    "endDate" to endDate.toString()
                ))
            }
        }
    }

    override suspend fun removeSemester(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_SEMESTER/$id")
        }
    }

    override suspend fun getCoursesOfSemester(semesterId: String): Result<List<edu.watumull.presencify.core.data.dto.academics.CourseDto>, DataError.Remote> {
        return safeCall<List<edu.watumull.presencify.core.data.dto.academics.CourseDto>> {
            httpClient.get(GET_COURSES_OF_SEMESTER) {
                parameter("semesterId", semesterId)
            }
        }
    }

    override suspend fun bulkCreateSemesters(semesters: List<Map<String, Any>>): Result<List<SemesterDto>, DataError.Remote> {
        return safeCall<List<SemesterDto>> {
            httpClient.post(BULK_CREATE_SEMESTERS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("semesters" to semesters))
            }
        }
    }

    override suspend fun bulkDeleteSemesters(semesterIds: List<String>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(BULK_DELETE_SEMESTERS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("semesterIds" to semesterIds))
            }
        }
    }
}