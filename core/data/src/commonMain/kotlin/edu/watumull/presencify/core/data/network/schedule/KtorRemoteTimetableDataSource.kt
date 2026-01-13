package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.dto.schedule.TimetableDto
import edu.watumull.presencify.core.data.dto.schedule.TimetableListWithTotalCountDto
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.ADD_TIMETABLE
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_TIMETABLES
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_TIMETABLE_BY_ID
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.REMOVE_TIMETABLE
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.UPDATE_TIMETABLE
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorRemoteTimetableDataSource(
    private val httpClient: HttpClient
) : RemoteTimetableDataSource {

    override suspend fun getTimetables(
        semesterNumber: SemesterNumber?,
        academicStartYearOfSemester: Int?,
        academicEndYearOfSemester: Int?,
        page: Int?,
        limit: Int?
    ): Result<TimetableListWithTotalCountDto, DataError.Remote> {
        return safeCall<TimetableListWithTotalCountDto> {
            httpClient.get(GET_TIMETABLES) {
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                academicStartYearOfSemester?.let { parameter("academicStartYearOfSemester", it) }
                academicEndYearOfSemester?.let { parameter("academicEndYearOfSemester", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
            }
        }
    }

    override suspend fun getTimetableById(id: String): Result<TimetableDto, DataError.Remote> {
        return safeCall<TimetableDto> {
            httpClient.get("$GET_TIMETABLE_BY_ID/$id")
        }
    }

    override suspend fun addTimetable(divisionId: String, timetableVersion: Int?): Result<TimetableDto, DataError.Remote> {
        return safeCall<TimetableDto> {
            httpClient.post(ADD_TIMETABLE) {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    put("divisionId", divisionId)
                    timetableVersion?.let { put("timetableVersion", it) }
                })
            }
        }
    }

    override suspend fun updateTimetable(id: String, timetableVersion: Int): Result<TimetableDto, DataError.Remote> {
        return safeCall<TimetableDto> {
            httpClient.put("$UPDATE_TIMETABLE/$id") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("timetableVersion" to timetableVersion))
            }
        }
    }

    override suspend fun removeTimetable(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_TIMETABLE/$id")
        }
    }
}
