package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.RoomListWithTotalCountDto
import edu.watumull.presencify.core.domain.model.schedule.RoomListWithTotalCount

fun RoomListWithTotalCountDto.toDomain(): RoomListWithTotalCount = RoomListWithTotalCount(
    rooms = rooms.map { it.toDomain() },
    totalCount = totalCount
)
