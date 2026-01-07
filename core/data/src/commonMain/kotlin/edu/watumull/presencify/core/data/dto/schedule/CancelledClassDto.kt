package edu.watumull.presencify.core.data.dto.schedule

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelledClassDto(
    val id: String,
    val classId: String,
    val cancelDate: LocalDate,
    val reason: String? = null,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Class")
    val klass: ClassDto? = null
)
