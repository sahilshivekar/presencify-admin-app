package edu.watumull.presencify.core.data.dto.schedule

import edu.watumull.presencify.core.data.dto.academics.DivisionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimetableDto(
    val id: String,
    val divisionId: String,
    val timetableVersion: Int,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Division")
    val division: DivisionDto? = null,
    @SerialName("Classes")
    val classes: List<ClassDto>? = null
)
