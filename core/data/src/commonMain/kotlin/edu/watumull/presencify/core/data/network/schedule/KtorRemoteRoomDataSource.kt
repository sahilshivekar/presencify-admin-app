package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.dto.schedule.RoomDto
import edu.watumull.presencify.core.data.dto.schedule.RoomListWithTotalCountDto
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.ADD_ROOM
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_ROOMS
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_ROOM_BY_ID
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.GET_ROOM_SCHEDULE
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.REMOVE_ROOM
import edu.watumull.presencify.core.data.network.schedule.ApiEndpoints.UPDATE_ROOM
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.RoomSortBy
import edu.watumull.presencify.core.domain.enums.RoomSortOrder
import edu.watumull.presencify.core.domain.enums.RoomType
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class KtorRemoteRoomDataSource(
    private val httpClient: HttpClient
) : RemoteRoomDataSource {

    override suspend fun getRooms(
        searchQuery: String?,
        sortBy: RoomSortBy?,
        sortOrder: RoomSortOrder?,
        busyBetweenStartTime: LocalTime?,
        busyBetweenEndTime: LocalTime?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?,
        type: RoomType?
    ): Result<RoomListWithTotalCountDto, DataError.Remote> {
        return safeCall<RoomListWithTotalCountDto> {
            httpClient.get(GET_ROOMS) {
                searchQuery?.let { parameter("searchQuery", it) }
                sortBy?.let { parameter("sortBy", it.value) }
                sortOrder?.let { parameter("sortOrder", it.value) }
                busyBetweenStartTime?.let { parameter("busyBetweenStartTime", it) }
                busyBetweenEndTime?.let { parameter("busyBetweenEndTime", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
                type?.let { parameter("type", it.value) }
            }
        }
    }

    override suspend fun addRoom(
        roomNumber: String,
        sittingCapacity: Int,
        name: String?,
        type: RoomType?
    ): Result<RoomDto, DataError.Remote> {
        return safeCall<RoomDto> {
            httpClient.post(ADD_ROOM) {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    put("roomNumber", roomNumber)
                    put("sittingCapacity", sittingCapacity)
                    name?.let { put("name", it) }
                    type?.let { put("type", it.value) }
                })
            }
        }
    }

    override suspend fun getRoomById(id: String): Result<RoomDto, DataError.Remote> {
        return safeCall<RoomDto> {
            httpClient.get("$GET_ROOM_BY_ID/$id")
        }
    }

    override suspend fun getRoomSchedule(
        id: String,
        startDate: LocalDate,
        endDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime
    ): Result<List<ClassDto>, DataError.Remote> {
        return safeCall<List<ClassDto>> {
            httpClient.get("$GET_ROOM_SCHEDULE/$id") {
                parameter("startDate", startDate.toString())
                parameter("endDate", endDate.toString())
                parameter("startTime", startTime.toString())
                parameter("endTime", endTime.toString())
            }
        }
    }

    override suspend fun updateRoom(
        id: String,
        roomNumber: String?,
        sittingCapacity: Int?,
        name: String?,
        type: RoomType?
    ): Result<RoomDto, DataError.Remote> {
        return safeCall<RoomDto> {
            httpClient.put("$UPDATE_ROOM/$id") {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    roomNumber?.let { put("roomNumber", it) }
                    sittingCapacity?.let { put("sittingCapacity", it) }
                    name?.let { put("name", it) }
                    type?.let { put("type", it.value) }
                })
            }
        }
    }

    override suspend fun removeRoom(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_ROOM/$id")
        }
    }
}
