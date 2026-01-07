package edu.watumull.presencify.core.data.repository.schedule

import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.data.network.schedule.RemoteTimetableDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.schedule.Timetable
import edu.watumull.presencify.core.domain.repository.schedule.TimetableRepository

class TimetableRepositoryImpl(
    private val remoteDataSource: RemoteTimetableDataSource
) : TimetableRepository {

    override suspend fun getTimetables(
        semesterNumber: SemesterNumber?,
        academicStartYearOfSemester: Int?,
        academicEndYearOfSemester: Int?,
        page: Int?,
        limit: Int?
    ): Result<List<Timetable>, DataError.Remote> {
        return remoteDataSource.getTimetables(
            semesterNumber,
            academicStartYearOfSemester,
            academicEndYearOfSemester,
            page,
            limit
        ).map { response ->
            response.timetables.map { it.toDomain() }
        }
    }

    override suspend fun getTimetableById(timetableId: String): Result<Timetable, DataError.Remote> {
        return remoteDataSource.getTimetableById(timetableId).map { it.toDomain() }
    }

    override suspend fun addTimetable(divisionId: String, timetableVersion: Int?): Result<Timetable, DataError.Remote> {
        return remoteDataSource.addTimetable(divisionId, timetableVersion).map { it.toDomain() }
    }

    override suspend fun updateTimetable(timetableId: String, timetableVersion: Int): Result<Timetable, DataError.Remote> {
        return remoteDataSource.updateTimetable(timetableId, timetableVersion).map { it.toDomain() }
    }

    override suspend fun removeTimetable(timetableId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeTimetable(timetableId)
    }
}
