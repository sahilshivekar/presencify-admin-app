package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.SchemeDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteSchemeDataSource {
    suspend fun getSchemes(searchQuery: String? = null): Result<List<SchemeDto>, DataError.Remote>

    suspend fun addScheme(
        name: String,
        universityId: String
    ): Result<SchemeDto, DataError.Remote>

    suspend fun getSchemeById(id: String): Result<SchemeDto, DataError.Remote>

    suspend fun updateScheme(
        id: String,
        name: String?
    ): Result<SchemeDto, DataError.Remote>

    suspend fun removeScheme(id: String): Result<Unit, DataError.Remote>
}