package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class DayOfWeek(val value: String) {
    @SerialName("Monday")
    MONDAY("Monday"),
    @SerialName("Tuesday")
    TUESDAY("Tuesday"),
    @SerialName("Wednesday")
    WEDNESDAY("Wednesday"),
    @SerialName("Thursday")
    THURSDAY("Thursday"),
    @SerialName("Friday")
    FRIDAY("Friday"),
    @SerialName("Saturday")
    SATURDAY("Saturday"),
    @SerialName("Sunday")
    SUNDAY("Sunday");

    companion object {
        fun fromValue(value: String): DayOfWeek? = entries.find { it.value == value }
    }
}