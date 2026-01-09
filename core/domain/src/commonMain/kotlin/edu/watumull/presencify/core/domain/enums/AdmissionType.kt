package edu.watumull.presencify.core.domain.enums

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import kotlinx.serialization.SerialName

enum class AdmissionType(val value: String) : DisplayLabelProvider {
    @SerialName("DSE")
    DSE("DSE"),
    @SerialName("FE")
    FE("FE");

    override fun toDisplayLabel(): String = value

    companion object {
        fun fromValue(value: String): AdmissionType? = entries.find { it.value == value }
    }
}