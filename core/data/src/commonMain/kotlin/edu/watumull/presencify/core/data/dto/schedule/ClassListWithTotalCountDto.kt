package edu.watumull.presencify.core.data.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class ClassListWithTotalCountDto(
    val classes: List<ClassDto>,
    val totalCount: Int
)
