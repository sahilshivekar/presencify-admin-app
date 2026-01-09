package edu.watumull.presencify.core.domain.enums

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import kotlinx.serialization.SerialName

enum class ClassType(val value: String) : DisplayLabelProvider {
    @SerialName("Lecture")
    LECTURE("Lecture"),
    @SerialName("Tutorial")
    TUTORIAL("Tutorial"),
    @SerialName("Practical")
    PRACTICAL("Practical");

    override fun toDisplayLabel(): String = value

    companion object {
        fun fromValue(value: String): ClassType? = entries.find { it.value == value }
    }
}