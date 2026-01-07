package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteBranchDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository

class BranchRepositoryImpl(
    private val remoteBranchDataSource: RemoteBranchDataSource
) : BranchRepository {

    override suspend fun getBranches(searchQuery: String?): Result<List<Branch>, DataError.Remote> {
        return remoteBranchDataSource.getBranches(searchQuery).map { it.map { branch -> branch.toDomain() } }
    }

    override suspend fun addBranch(name: String, abbreviation: String): Result<Branch, DataError.Remote> {
        return remoteBranchDataSource.addBranch(name, abbreviation).map { it.toDomain() }
    }

    override suspend fun getBranchById(id: String): Result<Branch, DataError.Remote> {
        return remoteBranchDataSource.getBranchById(id).map { it.toDomain() }
    }

    override suspend fun updateBranch(id: String, name: String?, abbreviation: String?): Result<Branch, DataError.Remote> {
        return remoteBranchDataSource.updateBranch(id, name, abbreviation).map { it.toDomain() }
    }

    override suspend fun removeBranch(id: String): Result<Unit, DataError.Remote> {
        return remoteBranchDataSource.removeBranch(id)
    }
}