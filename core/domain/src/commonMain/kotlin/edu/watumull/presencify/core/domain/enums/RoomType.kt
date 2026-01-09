package edu.watumull.presencify.core.domain.enums

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import kotlinx.serialization.SerialName

enum class RoomType(val value: String) : DisplayLabelProvider {
    @SerialName("Classroom")
    CLASSROOM("Classroom"),
    @SerialName("Lab")
    LAB("Lab"),
    @SerialName("Office")
    OFFICE("Office");

    override fun toDisplayLabel(): String = value

    companion object {
        fun fromValue(value: String): RoomType? = entries.find { it.value == value }
    }
}