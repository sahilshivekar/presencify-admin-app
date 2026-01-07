package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteUniversityDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.University
import edu.watumull.presencify.core.domain.repository.academics.UniversityRepository

class UniversityRepositoryImpl(
    private val remoteUniversityDataSource: RemoteUniversityDataSource
) : UniversityRepository {

    override suspend fun getUniversities(): Result<List<University>, DataError.Remote> {
        return remoteUniversityDataSource.getUniversities().map { it.map { university -> university.toDomain() } }
    }

    override suspend fun addUniversity(
        name: String,
        abbreviation: String?
    ): Result<University, DataError.Remote> {
        return remoteUniversityDataSource.addUniversity(name, abbreviation).map { it.toDomain() }
    }

    override suspend fun getUniversityById(id: String): Result<University, DataError.Remote> {
        return remoteUniversityDataSource.getUniversityById(id).map { it.toDomain() }
    }

    override suspend fun updateUniversity(
        id: String,
        name: String?,
        abbreviation: String?
    ): Result<University, DataError.Remote> {
        return remoteUniversityDataSource.updateUniversity(id, name, abbreviation).map { it.toDomain() }
    }

    override suspend fun removeUniversity(id: String): Result<Unit, DataError.Remote> {
        return remoteUniversityDataSource.removeUniversity(id)
    }
}