package edu.watumull.presencify.core.domain.enums

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import kotlinx.serialization.SerialName

enum class Gender(val value: String) : DisplayLabelProvider {
    @SerialName("Male")
    MALE("Male"),
    @SerialName("Female")
    FEMALE("Female"),
    @SerialName("Other")
    OTHER("Other");

    override fun toDisplayLabel(): String = value

    companion object {
        fun fromValue(value: String): Gender? = entries.find { it.value == value }
    }
}