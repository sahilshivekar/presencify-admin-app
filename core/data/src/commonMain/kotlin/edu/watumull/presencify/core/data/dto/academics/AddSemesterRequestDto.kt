package edu.watumull.presencify.core.data.dto.academics

import kotlinx.serialization.Serializable

@Serializable
data class AddSemesterRequestDto(
    val branchId: String,
    val semesterNumber: String,
    val academicStartYear: String,
    val academicEndYear: String,
    val startDate: String,
    val endDate: String,
    val schemeId: String,
    val optionalCourseIds: List<String>? = null
)
