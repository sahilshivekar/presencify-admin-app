package edu.watumull.presencify.core.data.repository.admin

import edu.watumull.presencify.core.data.mapper.admin.toDomain
import edu.watumull.presencify.core.data.network.admin.RemoteAdminDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.admin.AdminSortBy
import edu.watumull.presencify.core.domain.enums.admin.AdminSortOrder
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.admin.Admin
import edu.watumull.presencify.core.domain.repository.admin.AdminRepository

class AdminRepositoryImpl(
    private val remoteDataSource: RemoteAdminDataSource
) : AdminRepository {

    override suspend fun addAdmin(email: String, username: String, password: String): Result<Admin, DataError.Remote> {
        return remoteDataSource.addAdmin(email, username, password).map { it.toDomain() }
    }

    override suspend fun updateAdminDetails(email: String, username: String): Result<Admin, DataError.Remote> {
        return remoteDataSource.updateAdminDetails(email, username).map { it.toDomain() }
    }

    override suspend fun removeAdmin(): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeAdmin()
    }

    override suspend fun getAdmins(searchQuery: String?, sortBy: AdminSortBy, sortOrder: AdminSortOrder, page: Int, limit: Int): Result<List<Admin>, DataError.Remote> {
        return remoteDataSource.getAdmins(searchQuery, sortBy, sortOrder, page, limit).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getAdminDetails(): Result<Admin, DataError.Remote> {
        return remoteDataSource.getAdminDetails().map { it.toDomain() }
    }
}