package edu.watumull.presencify.core.data.repository.schedule

import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.data.network.schedule.RemoteClassSessionDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.schedule.CancelledClass
import edu.watumull.presencify.core.domain.model.schedule.ClassListWithTotalCount
import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import edu.watumull.presencify.core.domain.repository.schedule.ClassSessionRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class ClassSessionRepositoryImpl(
    private val remoteDataSource: RemoteClassSessionDataSource
) : ClassSessionRepository {

    override suspend fun getClasses(
        searchQuery: String?,
        timetableId: String?,
        divisionId: String?,
        startTime: LocalTime?,
        endTime: LocalTime?,
        activeFrom: LocalDate?,
        activeTill: LocalDate?,
        teacherId: String?,
        dayOfWeek: DayOfWeek?,
        roomId: String?,
        batchId: String?,
        classType: ClassType?,
        courseId: String?,
        semesterId: String?,
        isExtraClass: Boolean?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<ClassListWithTotalCount, DataError.Remote> {
        return remoteDataSource.getClasses(
            searchQuery, timetableId, divisionId, startTime, endTime, activeFrom, activeTill,
            teacherId, dayOfWeek, roomId, batchId, classType, courseId, semesterId, isExtraClass,
            page, limit, getAll
        ).map { it.toDomain() }
    }

    override suspend fun addClass(
        teacherId: String,
        startTime: LocalTime,
        endTime: LocalTime,
        dayOfWeek: DayOfWeek,
        roomId: String,
        batchId: String?,
        activeFrom: LocalDate,
        activeTill: LocalDate,
        classType: ClassType,
        courseId: String,
        timetableId: String
    ): Result<ClassSession, DataError.Remote> {
        return remoteDataSource.addClass(
            teacherId, startTime, endTime, dayOfWeek, roomId, batchId, activeFrom, activeTill,
            classType, courseId, timetableId
        ).map { it.toDomain() }
    }

    override suspend fun getClassById(classId: String): Result<ClassSession, DataError.Remote> {
        return remoteDataSource.getClassById(classId).map { it.toDomain() }
    }

    override suspend fun extendActiveTillDateOfClass(
        classId: String,
        newActiveTill: LocalDate
    ): Result<ClassSession, DataError.Remote> {
        return remoteDataSource.extendActiveTillDateOfClass(classId, newActiveTill).map { it.toDomain() }
    }

    override suspend fun removeClass(classId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeClass(classId)
    }

    override suspend fun addExtraClass(
        teacherId: String,
        startTime: LocalTime,
        endTime: LocalTime,
        dayOfWeek: DayOfWeek,
        roomId: String,
        batchId: String?,
        activeFrom: LocalDate,
        activeTill: LocalDate,
        classType: ClassType,
        courseId: String,
        timetableId: String
    ): Result<ClassSession, DataError.Remote> {
        return remoteDataSource.addExtraClass(
            teacherId, startTime, endTime, dayOfWeek, roomId, batchId, activeFrom, activeTill,
            classType, courseId, timetableId
        ).map { it.toDomain() }
    }

    override suspend fun getCancelledClasses(
        divisionId: String?,
        batchId: String?,
        date: LocalDate?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<List<CancelledClass>, DataError.Remote> {
        return remoteDataSource.getCancelledClasses(divisionId, batchId, date, page, limit, getAll).map { response ->
            response.cancelledClasses.map { it.toDomain() }
        }
    }

    override suspend fun cancelClass(
        classId: String,
        date: LocalDate,
        reason: String?
    ): Result<CancelledClass, DataError.Remote> {
        return remoteDataSource.cancelClass(classId, date, reason).map { it.toDomain() }
    }

    override suspend fun bulkCreateClasses(classes: List<Map<String, Any>>): Result<List<ClassSession>, DataError.Remote> {
        return remoteDataSource.bulkCreateClasses(classes).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkDeleteClasses(classIds: List<String>): Result<Unit, DataError.Remote> {
        return remoteDataSource.bulkDeleteClasses(classIds)
    }

    override suspend fun bulkCreateClassesFromCSV(): Result<List<ClassSession>, DataError.Remote> {
        return remoteDataSource.bulkCreateClassesFromCSV().map { list ->
            list.map { it.toDomain() }
        }
    }
}
