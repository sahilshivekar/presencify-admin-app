package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class AttendanceStatus(val value: String) {
    @SerialName("Present")
    PRESENT("Present"),
    @SerialName("Absent")
    ABSENT("Absent");

    companion object {
        fun fromValue(value: String): AttendanceStatus? = entries.find { it.value == value }
    }
}