package edu.watumull.presencify.core.domain.model.schedule

import edu.watumull.presencify.core.domain.enums.RoomType

data class Room(
    val id: String,
    val roomNumber: String,
    val name: String? = null,
    val type: RoomType? = null,
    val sittingCapacity: Int,
    val classes: List<ClassSession>? = null
)
