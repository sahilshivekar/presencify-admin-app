package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class RoomSortOrder(val value: String) {
    @SerialName("ASC")
    ASCENDING("ASC"),
    @SerialName("DESC")
    DESCENDING("DESC");

    companion object {
        fun fromValue(value: String): RoomSortOrder? = entries.find { it.value == value }
    }
}