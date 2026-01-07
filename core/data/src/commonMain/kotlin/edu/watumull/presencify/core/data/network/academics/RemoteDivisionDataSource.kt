package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.dto.academics.DivisionDto
import edu.watumull.presencify.core.data.dto.academics.DivisionListWithTotalCountDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber

interface RemoteDivisionDataSource {
    suspend fun getDivisions(
        searchQuery: String?,
        semesterNumber: SemesterNumber?,
        branchId: String?,
        semesterId: String?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<DivisionListWithTotalCountDto, DataError.Remote>

    suspend fun addDivision(
        divisionCode: String,
        semesterId: String
    ): Result<DivisionDto, DataError.Remote>

    suspend fun getDivisionById(id: String): Result<DivisionDto, DataError.Remote>

    suspend fun updateDivision(
        id: String,
        divisionCode: String
    ): Result<DivisionDto, DataError.Remote>

    suspend fun removeDivision(id: String): Result<Unit, DataError.Remote>
}