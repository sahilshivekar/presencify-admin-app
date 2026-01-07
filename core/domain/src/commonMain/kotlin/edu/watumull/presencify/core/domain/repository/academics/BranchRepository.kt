package edu.watumull.presencify.core.domain.repository.academics

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.model.academics.Branch

interface BranchRepository {
    suspend fun getBranches(searchQuery: String? = null): Result<List<Branch>, DataError.Remote>

    suspend fun addBranch(name: String, abbreviation: String): Result<Branch, DataError.Remote>

    suspend fun getBranchById(id: String): Result<Branch, DataError.Remote>

    suspend fun updateBranch(id: String, name: String? = null, abbreviation: String? = null): Result<Branch, DataError.Remote>

    suspend fun removeBranch(id: String): Result<Unit, DataError.Remote>
}