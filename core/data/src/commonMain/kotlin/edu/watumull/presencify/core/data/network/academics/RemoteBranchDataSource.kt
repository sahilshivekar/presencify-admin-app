package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.BranchDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteBranchDataSource {
    suspend fun getBranches(searchQuery: String? = null): Result<List<BranchDto>, DataError.Remote>

    suspend fun addBranch(name: String, abbreviation: String): Result<BranchDto, DataError.Remote>

    suspend fun getBranchById(id: String): Result<BranchDto, DataError.Remote>

    suspend fun updateBranch(id: String, name: String?, abbreviation: String?): Result<BranchDto, DataError.Remote>

    suspend fun removeBranch(id: String): Result<Unit, DataError.Remote>
}