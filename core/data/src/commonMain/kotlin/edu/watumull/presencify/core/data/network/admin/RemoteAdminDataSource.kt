package edu.watumull.presencify.core.data.network.admin

import edu.watumull.presencify.core.data.dto.admin.AdminDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.admin.AdminSortBy
import edu.watumull.presencify.core.domain.enums.admin.AdminSortOrder

interface RemoteAdminDataSource {
    suspend fun addAdmin(email: String, username: String, password: String): Result<AdminDto, DataError.Remote>
    suspend fun updateAdminDetails(email: String, username: String): Result<AdminDto, DataError.Remote>
    suspend fun removeAdmin(): Result<Unit, DataError.Remote>
    suspend fun getAdmins(searchQuery: String?, sortBy: AdminSortBy, sortOrder: AdminSortOrder, page: Int, limit: Int): Result<List<AdminDto>, DataError.Remote>
    suspend fun getAdminDetails(): Result<AdminDto, DataError.Remote>
}