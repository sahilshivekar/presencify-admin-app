package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.dto.schedule.CancelledClassDto
import edu.watumull.presencify.core.data.dto.schedule.CancelledClassListWithTotalCountDto
import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.dto.schedule.ClassListWithTotalCountDto
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.ADD_CLASS
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.ADD_EXTRA_CLASS
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.BULK_CREATE_CLASSES
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.BULK_CREATE_CLASSES_FROM_CSV
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.BULK_DELETE_CLASSES
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.CANCEL_CLASS
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.EXTEND_ACTIVE_TILL_DATE
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_CANCELLED_CLASSES
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_CLASSES
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_CLASS_BY_ID
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.REMOVE_CLASS
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class KtorRemoteClassSessionDataSource(
    private val httpClient: HttpClient
) : RemoteClassSessionDataSource {

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
    ): Result<ClassListWithTotalCountDto, DataError.Remote> {
        return safeCall<ClassListWithTotalCountDto> {
            httpClient.get(GET_CLASSES) {
                searchQuery?.let { parameter("searchQuery", it) }
                timetableId?.let { parameter("timetableId", it) }
                divisionId?.let { parameter("divisionId", it) }
                startTime?.let { parameter("startTime", it) }
                endTime?.let { parameter("endTime", it) }
                activeFrom?.let { parameter("activeFrom", it.toString()) }
                activeTill?.let { parameter("activeTill", it.toString()) }
                teacherId?.let { parameter("teacherId", it) }
                dayOfWeek?.value?.let { parameter("dayOfWeek", it) }
                roomId?.let { parameter("roomId", it) }
                batchId?.let { parameter("batchId", it) }
                classType?.value?.let { parameter("classType", it) }
                courseId?.let { parameter("courseId", it) }
                semesterId?.let { parameter("semesterId", it) }
                isExtraClass?.let { parameter("isExtraClass", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
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
    ): Result<ClassDto, DataError.Remote> {
        return safeCall<ClassDto> {
            httpClient.post(ADD_CLASS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "teacherId" to teacherId,
                    "startTime" to startTime,
                    "endTime" to endTime,
                    "dayOfWeek" to dayOfWeek.value,
                    "roomId" to roomId,
                    "batchId" to batchId,
                    "activeFrom" to activeFrom.toString(),
                    "activeTill" to activeTill.toString(),
                    "classType" to classType.value,
                    "courseId" to courseId,
                    "timetableId" to timetableId
                ))
            }
        }
    }

    override suspend fun getClassById(classId: String): Result<ClassDto, DataError.Remote> {
        return safeCall<ClassDto> {
            httpClient.get("$GET_CLASS_BY_ID/$classId")
        }
    }

    override suspend fun extendActiveTillDateOfClass(classId: String, newActiveTill: LocalDate): Result<ClassDto, DataError.Remote> {
        return safeCall<ClassDto> {
            httpClient.put("$EXTEND_ACTIVE_TILL_DATE/$classId") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("newActiveTill" to newActiveTill.toString()))
            }
        }
    }

    override suspend fun removeClass(classId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_CLASS/$classId")
        }
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
    ): Result<ClassDto, DataError.Remote> {
        return safeCall<ClassDto> {
            httpClient.post(ADD_EXTRA_CLASS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "teacherId" to teacherId,
                    "startTime" to startTime,
                    "endTime" to endTime,
                    "dayOfWeek" to dayOfWeek.value,
                    "roomId" to roomId,
                    "batchId" to batchId,
                    "activeFrom" to activeFrom.toString(),
                    "activeTill" to activeTill.toString(),
                    "classType" to classType.value,
                    "courseId" to courseId,
                    "timetableId" to timetableId
                ))
            }
        }
    }

    override suspend fun getCancelledClasses(
        divisionId: String?,
        batchId: String?,
        date: LocalDate?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<CancelledClassListWithTotalCountDto, DataError.Remote> {
        return safeCall<CancelledClassListWithTotalCountDto> {
            httpClient.get(GET_CANCELLED_CLASSES) {
                divisionId?.let { parameter("divisionId", it) }
                batchId?.let { parameter("batchId", it) }
                date?.let { parameter("date", it.toString()) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun cancelClass(
        classId: String,
        date: LocalDate,
        reason: String?
    ): Result<CancelledClassDto, DataError.Remote> {
        return safeCall<CancelledClassDto> {
            httpClient.post(CANCEL_CLASS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "classId" to classId,
                    "date" to date.toString(),
                    "reason" to reason
                ))
            }
        }
    }

    override suspend fun bulkCreateClasses(classes: List<Map<String, Any>>): Result<List<ClassDto>, DataError.Remote> {
        return safeCall<List<ClassDto>> {
            httpClient.post(BULK_CREATE_CLASSES) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("classes" to classes))
            }
        }
    }

    override suspend fun bulkDeleteClasses(classIds: List<String>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(BULK_DELETE_CLASSES) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("classIds" to classIds))
            }
        }
    }

    override suspend fun bulkCreateClassesFromCSV(): Result<List<ClassDto>, DataError.Remote> {
        return safeCall<List<ClassDto>> {
            httpClient.post(BULK_CREATE_CLASSES_FROM_CSV) {
                // File upload handled by middleware
            }
        }
    }
}
