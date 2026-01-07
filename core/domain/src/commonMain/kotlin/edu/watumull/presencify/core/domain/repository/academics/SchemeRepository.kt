package edu.watumull.presencify.core.domain.repository.academics

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.model.academics.Scheme

interface SchemeRepository {
    suspend fun getSchemes(searchQuery: String? = null): Result<List<Scheme>, DataError.Remote>

    suspend fun addScheme(
        name: String,
        universityId: String
    ): Result<Scheme, DataError.Remote>

    suspend fun getSchemeById(id: String): Result<Scheme, DataError.Remote>

    suspend fun updateScheme(
        id: String,
        name: String? = null
    ): Result<Scheme, DataError.Remote>

    suspend fun removeScheme(id: String): Result<Unit, DataError.Remote>
}