package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.UniversityDto
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.ADD_UNIVERSITY
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_UNIVERSITIES
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.GET_UNIVERSITY_BY_ID
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.REMOVE_UNIVERSITY
import edu.watumull.presencify.core.data.network.academics.ApiEndpoints.UPDATE_UNIVERSITY
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorRemoteUniversityDataSource(
    private val httpClient: HttpClient
) : RemoteUniversityDataSource {

    override suspend fun getUniversities(): Result<List<UniversityDto>, DataError.Remote> {
        return safeCall<List<UniversityDto>> {
            httpClient.get(GET_UNIVERSITIES)
        }
    }

    override suspend fun addUniversity(
        name: String,
        abbreviation: String?
    ): Result<UniversityDto, DataError.Remote> {
        return safeCall<UniversityDto> {
            httpClient.post(ADD_UNIVERSITY) {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    put("name", name)
                    abbreviation?.let { put("abbreviation", it) }
                })
            }
        }
    }

    override suspend fun getUniversityById(id: String): Result<UniversityDto, DataError.Remote> {
        return safeCall<UniversityDto> {
            httpClient.get("$GET_UNIVERSITY_BY_ID/$id")
        }
    }

    override suspend fun updateUniversity(
        id: String,
        name: String?,
        abbreviation: String?
    ): Result<UniversityDto, DataError.Remote> {
        return safeCall<UniversityDto> {
            httpClient.put("$UPDATE_UNIVERSITY/$id") {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    name?.let { put("name", it) }
                    abbreviation?.let { put("abbreviation", it) }
                })
            }
        }
    }

    override suspend fun removeUniversity(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_UNIVERSITY/$id")
        }
    }
}