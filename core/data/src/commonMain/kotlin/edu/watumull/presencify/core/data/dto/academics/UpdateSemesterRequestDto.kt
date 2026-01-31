package edu.watumull.presencify.core.data.dto.academics

import kotlinx.serialization.Serializable

@Serializable
data class UpdateSemesterRequestDto(
    val startDate: String,
    val endDate: String,
    val optionalCourseIds: List<String>? = null
)
