package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.UniversityDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteUniversityDataSource {
    suspend fun getUniversities(): Result<List<UniversityDto>, DataError.Remote>

    suspend fun addUniversity(
        name: String,
        abbreviation: String?
    ): Result<UniversityDto, DataError.Remote>

    suspend fun getUniversityById(id: String): Result<UniversityDto, DataError.Remote>

    suspend fun updateUniversity(
        id: String,
        name: String?,
        abbreviation: String?
    ): Result<UniversityDto, DataError.Remote>

    suspend fun removeUniversity(id: String): Result<Unit, DataError.Remote>
}