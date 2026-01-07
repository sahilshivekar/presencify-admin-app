package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class RoomSortBy(val value: String) {
    @SerialName("roomNumber")
    ROOM_NUMBER("roomNumber"),
    @SerialName("sittingCapacity")
    SITTING_CAPACITY("sittingCapacity");

    companion object {
        fun fromValue(value: String): RoomSortBy? = entries.find { it.value == value }
    }
}