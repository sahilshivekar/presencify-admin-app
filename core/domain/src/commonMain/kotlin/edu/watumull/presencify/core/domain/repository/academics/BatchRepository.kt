package edu.watumull.presencify.core.domain.repository.academics

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Batch

interface BatchRepository {
    suspend fun getBatches(
        semesterNumber: SemesterNumber? = null,
        branchId: String? = null,
        divisionId: String? = null,
        academicStartYear: Int? = null,
        academicEndYear: Int? = null,
        searchQuery: String? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null
    ): Result<List<Batch>, DataError.Remote>

    suspend fun addBatch(batchCode: String, divisionId: String): Result<Batch, DataError.Remote>

    suspend fun getBatchById(id: String): Result<Batch, DataError.Remote>

    suspend fun updateBatch(id: String, batchCode: String): Result<Batch, DataError.Remote>

    suspend fun removeBatch(id: String): Result<Unit, DataError.Remote>
}