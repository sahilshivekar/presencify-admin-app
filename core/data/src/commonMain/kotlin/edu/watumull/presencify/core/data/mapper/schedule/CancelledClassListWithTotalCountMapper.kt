package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.CancelledClassListWithTotalCountDto
import edu.watumull.presencify.core.domain.model.schedule.CancelledClassListWithTotalCount

fun CancelledClassListWithTotalCountDto.toDomain(): CancelledClassListWithTotalCount = CancelledClassListWithTotalCount(
    cancelledClasses = cancelledClasses.map { it.toDomain() },
    totalCount = totalCount
)
