package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class RoomType(val value: String) {
    @SerialName("Classroom")
    CLASSROOM("Classroom"),
    @SerialName("Lab")
    LAB("Lab"),
    @SerialName("Office")
    OFFICE("Office");

    companion object {
        fun fromValue(value: String): RoomType? = entries.find { it.value == value }
    }
}