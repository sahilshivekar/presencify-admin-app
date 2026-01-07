package edu.watumull.presencify.core.domain.model.schedule

data class CancelledClassListWithTotalCount(
    val cancelledClasses: List<CancelledClass>,
    val totalCount: Int
)