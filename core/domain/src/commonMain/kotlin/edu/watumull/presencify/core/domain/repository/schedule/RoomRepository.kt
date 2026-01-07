package edu.watumull.presencify.core.domain.repository.schedule

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.RoomSortBy
import edu.watumull.presencify.core.domain.enums.RoomSortOrder
import edu.watumull.presencify.core.domain.enums.RoomType
import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import edu.watumull.presencify.core.domain.model.schedule.Room
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface RoomRepository {
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
    ): Result<List<Room>, DataError.Remote>

    suspend fun addRoom(
        roomNumber: String,
        sittingCapacity: Int,
        name: String? = null,
        type: RoomType? = null
    ): Result<Room, DataError.Remote>

    suspend fun getRoomById(roomId: String): Result<Room, DataError.Remote>

    suspend fun getRoomSchedule(
        roomId: String,
        startDate: LocalDate,
        endDate: LocalDate,
        startTime: LocalTime,
        endTime: LocalTime
    ): Result<List<ClassSession>, DataError.Remote>

    suspend fun updateRoom(
        roomId: String,
        roomNumber: String? = null,
        sittingCapacity: Int? = null,
        name: String? = null,
        type: RoomType? = null
    ): Result<Room, DataError.Remote>

    suspend fun removeRoom(roomId: String): Result<Unit, DataError.Remote>
}
