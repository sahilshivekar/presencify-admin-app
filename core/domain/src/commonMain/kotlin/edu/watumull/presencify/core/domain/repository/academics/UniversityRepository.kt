package edu.watumull.presencify.core.domain.repository.academics

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.model.academics.University

interface UniversityRepository {
    suspend fun getUniversities(): Result<List<University>, DataError.Remote>

    suspend fun addUniversity(
        name: String,
        abbreviation: String?
    ): Result<University, DataError.Remote>

    suspend fun getUniversityById(id: String): Result<University, DataError.Remote>

    suspend fun updateUniversity(
        id: String,
        name: String?,
        abbreviation: String?
    ): Result<University, DataError.Remote>

    suspend fun removeUniversity(id: String): Result<Unit, DataError.Remote>
}