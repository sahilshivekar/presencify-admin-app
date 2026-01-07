package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteSchemeDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository

class SchemeRepositoryImpl(
    private val remoteSchemeDataSource: RemoteSchemeDataSource
) : SchemeRepository {

    override suspend fun getSchemes(searchQuery: String?): Result<List<Scheme>, DataError.Remote> {
        return remoteSchemeDataSource.getSchemes(searchQuery).map { it.map { scheme -> scheme.toDomain() } }
    }

    override suspend fun addScheme(
        name: String,
        universityId: String
    ): Result<Scheme, DataError.Remote> {
        return remoteSchemeDataSource.addScheme(name, universityId).map { it.toDomain() }
    }

    override suspend fun getSchemeById(id: String): Result<Scheme, DataError.Remote> {
        return remoteSchemeDataSource.getSchemeById(id).map { it.toDomain() }
    }

    override suspend fun updateScheme(
        id: String,
        name: String?
    ): Result<Scheme, DataError.Remote> {
        return remoteSchemeDataSource.updateScheme(id, name).map { it.toDomain() }
    }

    override suspend fun removeScheme(id: String): Result<Unit, DataError.Remote> {
        return remoteSchemeDataSource.removeScheme(id)
    }
}