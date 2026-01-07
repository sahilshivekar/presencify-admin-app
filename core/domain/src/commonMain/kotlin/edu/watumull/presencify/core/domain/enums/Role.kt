package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class Role(val value: String) {
    @SerialName("Teacher")
    TEACHER("Teacher"),
    @SerialName("Head of Department")
    HEAD_OF_DEPARTMENT("Head of Department"),
    @SerialName("Principal")
    PRINCIPAL("Principal");

    companion object {
        fun fromValue(value: String): Role? = entries.find { it.value == value }
    }
}