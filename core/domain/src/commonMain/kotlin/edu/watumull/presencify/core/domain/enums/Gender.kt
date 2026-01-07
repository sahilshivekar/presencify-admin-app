package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class Gender(val value: String) {
    @SerialName("Male")
    MALE("Male"),
    @SerialName("Female")
    FEMALE("Female"),
    @SerialName("Other")
    OTHER("Other");

    companion object {
        fun fromValue(value: String): Gender? = entries.find { it.value == value }
    }
}