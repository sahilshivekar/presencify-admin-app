package edu.watumull.presencify.core.domain.enums

import kotlinx.serialization.SerialName

enum class AdmissionType(val value: String) {
    @SerialName("DSE")
    DSE("DSE"),
    @SerialName("FE")
    FE("FE");

    companion object {
        fun fromValue(value: String): AdmissionType? = entries.find { it.value == value }
    }
}