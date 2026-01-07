package edu.watumull.presencify.core.domain.model.schedule

data class RoomListWithTotalCount(
    val rooms: List<Room>,
    val totalCount: Int
)