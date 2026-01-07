package edu.watumull.presencify.core.domain.repository.schedule

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import edu.watumull.presencify.core.domain.model.schedule.CancelledClass
import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface ClassSessionRepository {
    suspend fun getClasses(
        searchQuery: String? = null,
        timetableId: String? = null,
        divisionId: String? = null,
        startTime: LocalTime? = null,
        endTime: LocalTime? = null,
        activeFrom: LocalDate? = null,
        activeTill: LocalDate? = null,
        teacherId: String? = null,
        dayOfWeek: DayOfWeek? = null,
        roomId: String? = null,
        batchId: String? = null,
        classType: ClassType? = null,
        courseId: String? = null,
        semesterId: String? = null,
        isExtraClass: Boolean? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null
    ): Result<List<ClassSession>, DataError.Remote>

    suspend fun addClass(
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
    ): Result<ClassSession, DataError.Remote>

    suspend fun getClassById(classId: String): Result<ClassSession, DataError.Remote>

    suspend fun extendActiveTillDateOfClass(classId: String, newActiveTill: LocalDate): Result<ClassSession, DataError.Remote>

    suspend fun removeClass(classId: String): Result<Unit, DataError.Remote>

    suspend fun addExtraClass(
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
    ): Result<ClassSession, DataError.Remote>

    suspend fun getCancelledClasses(
        divisionId: String? = null,
        batchId: String? = null,
        date: LocalDate? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null
    ): Result<List<CancelledClass>, DataError.Remote>

    suspend fun cancelClass(
        classId: String,
        date: LocalDate,
        reason: String?
    ): Result<CancelledClass, DataError.Remote>

    suspend fun bulkCreateClasses(classes: List<Map<String, Any>>): Result<List<ClassSession>, DataError.Remote>

    suspend fun bulkDeleteClasses(classIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkCreateClassesFromCSV(): Result<List<ClassSession>, DataError.Remote>
}
