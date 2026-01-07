package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteBatchDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.Batch
import edu.watumull.presencify.core.domain.repository.academics.BatchRepository

class BatchRepositoryImpl(
    private val remoteBatchDataSource: RemoteBatchDataSource
) : BatchRepository {

    override suspend fun getBatches(
        semesterNumber: SemesterNumber?,
        branchId: String?,
        divisionId: String?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        searchQuery: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<List<Batch>, DataError.Remote> {
        return remoteBatchDataSource.getBatches(
            semesterNumber, branchId, divisionId, academicStartYear, academicEndYear,
            searchQuery, page, limit, getAll
        ).map { it.batches.map { batch -> batch.toDomain() } }
    }

    override suspend fun addBatch(batchCode: String, divisionId: String): Result<Batch, DataError.Remote> {
        return remoteBatchDataSource.addBatch(batchCode, divisionId).map { it.toDomain() }
    }

    override suspend fun getBatchById(id: String): Result<Batch, DataError.Remote> {
        return remoteBatchDataSource.getBatchById(id).map { it.toDomain() }
    }

    override suspend fun updateBatch(id: String, batchCode: String): Result<Batch, DataError.Remote> {
        return remoteBatchDataSource.updateBatch(id, batchCode).map { it.toDomain() }
    }

    override suspend fun removeBatch(id: String): Result<Unit, DataError.Remote> {
        return remoteBatchDataSource.removeBatch(id)
    }
}