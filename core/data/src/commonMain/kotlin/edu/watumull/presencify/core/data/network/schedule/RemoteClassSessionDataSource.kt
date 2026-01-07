package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.dto.schedule.CancelledClassDto
import edu.watumull.presencify.core.data.dto.schedule.CancelledClassListWithTotalCountDto
import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.dto.schedule.ClassListWithTotalCountDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface RemoteClassSessionDataSource {
    suspend fun getClasses(
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
    ): Result<ClassListWithTotalCountDto, DataError.Remote>

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
    ): Result<ClassDto, DataError.Remote>

    suspend fun getClassById(classId: String): Result<ClassDto, DataError.Remote>

    suspend fun extendActiveTillDateOfClass(classId: String, newActiveTill: LocalDate): Result<ClassDto, DataError.Remote>

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
    ): Result<ClassDto, DataError.Remote>

    suspend fun getCancelledClasses(
        divisionId: String?,
        batchId: String?,
        date: LocalDate?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<CancelledClassListWithTotalCountDto, DataError.Remote>

    suspend fun cancelClass(
        classId: String,
        date: LocalDate,
        reason: String?
    ): Result<CancelledClassDto, DataError.Remote>

    suspend fun bulkCreateClasses(classes: List<Map<String, Any>>): Result<List<ClassDto>, DataError.Remote>

    suspend fun bulkDeleteClasses(classIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkCreateClassesFromCSV(): Result<List<ClassDto>, DataError.Remote>
}
