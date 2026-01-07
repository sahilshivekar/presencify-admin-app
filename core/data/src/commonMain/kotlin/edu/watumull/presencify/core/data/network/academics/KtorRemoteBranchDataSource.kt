package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.BranchDto
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.ADD_BRANCH
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_BRANCHES
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_BRANCH_BY_ID
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.REMOVE_BRANCH
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.UPDATE_BRANCH
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteBranchDataSource(
    private val httpClient: HttpClient
) : RemoteBranchDataSource {

    override suspend fun getBranches(searchQuery: String?): Result<List<BranchDto>, DataError.Remote> {
        return safeCall<List<BranchDto>> {
            httpClient.get(GET_BRANCHES) {
                searchQuery?.let { parameter("searchQuery", it) }
            }
        }
    }

    override suspend fun addBranch(name: String, abbreviation: String): Result<BranchDto, DataError.Remote> {
        return safeCall<BranchDto> {
            httpClient.post(ADD_BRANCH) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("name" to name, "abbreviation" to abbreviation))
            }
        }
    }

    override suspend fun getBranchById(id: String): Result<BranchDto, DataError.Remote> {
        return safeCall<BranchDto> {
            httpClient.get("$GET_BRANCH_BY_ID/$id")
        }
    }

    override suspend fun updateBranch(id: String, name: String?, abbreviation: String?): Result<BranchDto, DataError.Remote> {
        val body = mutableMapOf<String, String>()
        name?.let { body["name"] = it }
        abbreviation?.let { body["abbreviation"] = it }
        return safeCall<BranchDto> {
            httpClient.put("$UPDATE_BRANCH/$id") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }

    override suspend fun removeBranch(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_BRANCH/$id")
        }
    }
}