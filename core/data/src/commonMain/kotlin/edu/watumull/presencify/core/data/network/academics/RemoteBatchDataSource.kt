package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.BatchDto
import edu.watumull.presencify.core.data.dto.academics.BatchListWithTotalCountDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber

interface RemoteBatchDataSource {
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
    ): Result<BatchListWithTotalCountDto, DataError.Remote>

    suspend fun addBatch(batchCode: String, divisionId: String): Result<BatchDto, DataError.Remote>

    suspend fun getBatchById(id: String): Result<BatchDto, DataError.Remote>

    suspend fun updateBatch(id: String, batchCode: String): Result<BatchDto, DataError.Remote>

    suspend fun removeBatch(id: String): Result<Unit, DataError.Remote>
}