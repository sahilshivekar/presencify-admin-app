package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.RoomDto
import edu.watumull.presencify.core.domain.model.schedule.Room

fun RoomDto.toDomain(): Room = Room(
    id = id,
    roomNumber = roomNumber,
    name = name,
    type = type,
    sittingCapacity = sittingCapacity,
    classes = classes?.map { it.toDomain() }
)
