package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.DivisionDto
import edu.watumull.presencify.core.data.dto.academics.DivisionListWithTotalCountDto
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.ADD_DIVISION
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_DIVISIONS
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_DIVISION_BY_ID
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.REMOVE_DIVISION
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.UPDATE_DIVISION
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorRemoteDivisionDataSource(
    private val httpClient: HttpClient
) : RemoteDivisionDataSource {

    override suspend fun getDivisions(
        searchQuery: String?,
        semesterNumber: SemesterNumber?,
        branchId: String?,
        semesterId: String?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<DivisionListWithTotalCountDto, DataError.Remote> {
        return safeCall<DivisionListWithTotalCountDto> {
            httpClient.get(GET_DIVISIONS) {
                searchQuery?.let { parameter("searchQuery", it) }
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                branchId?.let { parameter("branchId", it) }
                semesterId?.let { parameter("semesterId", it) }
                academicStartYear?.let { parameter("academicStartYear", it) }
                academicEndYear?.let { parameter("academicEndYear", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun addDivision(
        divisionCode: String,
        semesterId: String
    ): Result<DivisionDto, DataError.Remote> {
        return safeCall<DivisionDto> {
            httpClient.post(ADD_DIVISION) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "divisionCode" to divisionCode,
                    "semesterId" to semesterId
                ))
            }
        }
    }

    override suspend fun getDivisionById(id: String): Result<DivisionDto, DataError.Remote> {
        return safeCall<DivisionDto> {
            httpClient.get("$GET_DIVISION_BY_ID/$id")
        }
    }

    override suspend fun updateDivision(
        id: String,
        divisionCode: String
    ): Result<DivisionDto, DataError.Remote> {
        return safeCall<DivisionDto> {
            httpClient.put("$UPDATE_DIVISION/$id") {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "divisionCode" to divisionCode
                ))
            }
        }
    }

    override suspend fun removeDivision(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_DIVISION/$id")
        }
    }
}