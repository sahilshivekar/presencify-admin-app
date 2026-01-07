package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class ClassType(val value: String) {
    @SerialName("Lecture")
    LECTURE("Lecture"),
    @SerialName("Tutorial")
    TUTORIAL("Tutorial"),
    @SerialName("Practical")
    PRACTICAL("Practical");

    companion object {
        fun fromValue(value: String): ClassType? = entries.find { it.value == value }
    }
}