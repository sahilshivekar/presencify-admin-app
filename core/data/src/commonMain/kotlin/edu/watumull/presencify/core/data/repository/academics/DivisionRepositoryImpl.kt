package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteDivisionDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.Division
import edu.watumull.presencify.core.domain.repository.academics.DivisionRepository

class DivisionRepositoryImpl(
    private val remoteDivisionDataSource: RemoteDivisionDataSource
) : DivisionRepository {

    override suspend fun getDivisions(
        searchQuery: String?,
        semesterNumber: SemesterNumber?,
        branchId: String?,
        semesterId: String?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<List<Division>, DataError.Remote> {
        return remoteDivisionDataSource.getDivisions(
            searchQuery, semesterNumber, branchId, semesterId, academicStartYear, academicEndYear, page, limit, getAll
        ).map { it.divisions.map { division -> division.toDomain() } }
    }

    override suspend fun addDivision(
        divisionCode: String,
        semesterId: String
    ): Result<Division, DataError.Remote> {
        return remoteDivisionDataSource.addDivision(divisionCode, semesterId).map { it.toDomain() }
    }

    override suspend fun getDivisionById(id: String): Result<Division, DataError.Remote> {
        return remoteDivisionDataSource.getDivisionById(id).map { it.toDomain() }
    }

    override suspend fun updateDivision(
        id: String,
        divisionCode: String
    ): Result<Division, DataError.Remote> {
        return remoteDivisionDataSource.updateDivision(id, divisionCode).map { it.toDomain() }
    }

    override suspend fun removeDivision(id: String): Result<Unit, DataError.Remote> {
        return remoteDivisionDataSource.removeDivision(id)
    }
}