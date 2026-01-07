package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.dto.schedule.TimetableDto
import edu.watumull.presencify.core.data.dto.schedule.TimetableListWithTotalCountDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber

interface RemoteTimetableDataSource {
    suspend fun getTimetables(
        semesterNumber: SemesterNumber?,
        academicStartYearOfSemester: Int?,
        academicEndYearOfSemester: Int?,
        page: Int?,
        limit: Int?
    ): Result<TimetableListWithTotalCountDto, DataError.Remote>

    suspend fun getTimetableById(id: String): Result<TimetableDto, DataError.Remote>

    suspend fun addTimetable(divisionId: String, timetableVersion: Int?): Result<TimetableDto, DataError.Remote>

    suspend fun updateTimetable(id: String, timetableVersion: Int): Result<TimetableDto, DataError.Remote>

    suspend fun removeTimetable(id: String): Result<Unit, DataError.Remote>
}
