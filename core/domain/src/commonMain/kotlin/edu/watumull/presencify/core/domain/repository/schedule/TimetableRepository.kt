package edu.watumull.presencify.core.domain.repository.schedule

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.schedule.Timetable

interface TimetableRepository {
    suspend fun getTimetables(
        semesterNumber: SemesterNumber?,
        academicStartYearOfSemester: Int?,
        academicEndYearOfSemester: Int?,
        page: Int?,
        limit: Int?
    ): Result<List<Timetable>, DataError.Remote>

    suspend fun getTimetableById(timetableId: String): Result<Timetable, DataError.Remote>

    suspend fun addTimetable(divisionId: String, timetableVersion: Int?): Result<Timetable, DataError.Remote>

    suspend fun updateTimetable(timetableId: String, timetableVersion: Int): Result<Timetable, DataError.Remote>

    suspend fun removeTimetable(timetableId: String): Result<Unit, DataError.Remote>
}
