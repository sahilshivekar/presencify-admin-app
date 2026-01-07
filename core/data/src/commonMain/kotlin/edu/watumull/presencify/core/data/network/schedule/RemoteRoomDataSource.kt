package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.dto.schedule.RoomDto
import edu.watumull.presencify.core.data.dto.schedule.RoomListWithTotalCountDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.RoomSortBy
import edu.watumull.presencify.core.domain.enums.RoomSortOrder
import edu.watumull.presencify.core.domain.enums.RoomType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface RemoteRoomDataSource {
    suspend fun getRooms(
        searchQuery: String? = null,
        sortBy: RoomSortBy? = null,
        sortOrder: RoomSortOrder? = null,
        busyBetweenStartTime: LocalTime? = null,
        busyBetweenEndTime: LocalTime? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null,
        type: RoomType? = null
    ): Result<RoomListWithTotalCountDto, DataError.Remote>

    suspend fun addRoom(
        roomNumber: String,
        sittingCapacity: Int,
        name: String? = null,
        type: RoomType? = null
    ): Result<RoomDto, DataError.Remote>

    suspend fun getRoomById(id: String): Result<RoomDto, DataError.Remote>

    suspend fun getRoomSchedule(
        id: String,
        startDate: LocalDate,
        endDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime
    ): Result<List<ClassDto>, DataError.Remote>

    suspend fun updateRoom(
        id: String,
        roomNumber: String? = null,
        sittingCapacity: Int? = null,
        name: String? = null,
        type: RoomType? = null
    ): Result<RoomDto, DataError.Remote>

    suspend fun removeRoom(id: String): Result<Unit, DataError.Remote>
}
