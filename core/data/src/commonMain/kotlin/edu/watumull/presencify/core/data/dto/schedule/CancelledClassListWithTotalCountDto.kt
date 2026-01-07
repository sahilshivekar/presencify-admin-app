package edu.watumull.presencify.core.data.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class CancelledClassListWithTotalCountDto(
    val cancelledClasses: List<CancelledClassDto>,
    val totalCount: Int
)
