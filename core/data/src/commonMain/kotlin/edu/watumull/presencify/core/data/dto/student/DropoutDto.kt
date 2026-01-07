package edu.watumull.presencify.core.data.dto.student

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DropoutDto(
    val id: String,
    val studentId: String,
    val reason: String? = null,
    val dropoutDate: LocalDate,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Student")
    val student: StudentDto? = null
)
