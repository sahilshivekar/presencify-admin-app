package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.BatchDto
import edu.watumull.presencify.core.data.dto.academics.BatchListWithTotalCountDto
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.ADD_BATCH
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_BATCHES
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_BATCH_BY_ID
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.REMOVE_BATCH
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.UPDATE_BATCH
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

class KtorRemoteBatchDataSource(
    private val httpClient: HttpClient
) : RemoteBatchDataSource {

    override suspend fun getBatches(
        semesterNumber: SemesterNumber?,
        branchId: String?,
        divisionId: String?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        searchQuery: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<BatchListWithTotalCountDto, DataError.Remote> {
        return safeCall<BatchListWithTotalCountDto> {
            httpClient.get(GET_BATCHES) {
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                branchId?.let { parameter("branchId", it) }
                divisionId?.let { parameter("divisionId", it) }
                academicStartYear?.let { parameter("academicStartYear", it) }
                academicEndYear?.let { parameter("academicEndYear", it) }
                searchQuery?.let { parameter("searchQuery", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun addBatch(batchCode: String, divisionId: String): Result<BatchDto, DataError.Remote> {
        return safeCall<BatchDto> {
            httpClient.post(ADD_BATCH) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("batchCode" to batchCode, "divisionId" to divisionId))
            }
        }
    }

    override suspend fun getBatchById(id: String): Result<BatchDto, DataError.Remote> {
        return safeCall<BatchDto> {
            httpClient.get("$GET_BATCH_BY_ID/$id")
        }
    }

    override suspend fun updateBatch(id: String, batchCode: String): Result<BatchDto, DataError.Remote> {
        return safeCall<BatchDto> {
            httpClient.put("$UPDATE_BATCH/$id") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("batchCode" to batchCode))
            }
        }
    }

    override suspend fun removeBatch(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_BATCH/$id")
        }
    }
}