package edu.watumull.presencify.core.domain.enums

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import kotlinx.serialization.SerialName

enum class TeacherRole(val value: String) : DisplayLabelProvider {
    @SerialName("Teacher")
    TEACHER("Teacher"),
    @SerialName("Head of Department")
    HEAD_OF_DEPARTMENT("Head of Department"),
    @SerialName("Principal")
    PRINCIPAL("Principal");

    override fun toDisplayLabel(): String = value

    companion object {
        fun fromValue(value: String): TeacherRole? = entries.find { it.value == value }
    }
}