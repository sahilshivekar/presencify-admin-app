package edu.watumull.presencify.core.domain.repository.admin

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.admin.AdminSortBy
import edu.watumull.presencify.core.domain.enums.admin.AdminSortOrder
import edu.watumull.presencify.core.domain.model.admin.Admin

interface AdminRepository {
    suspend fun addAdmin(email: String, username: String, password: String): Result<Admin, DataError.Remote>
    suspend fun updateAdminDetails(email: String, username: String): Result<Admin, DataError.Remote>
    suspend fun removeAdmin(): Result<Unit, DataError.Remote>
    suspend fun getAdmins(searchQuery: String?, sortBy: AdminSortBy, sortOrder: AdminSortOrder, page: Int, limit: Int): Result<List<Admin>, DataError.Remote>
    suspend fun getAdminDetails(): Result<Admin, DataError.Remote>
}