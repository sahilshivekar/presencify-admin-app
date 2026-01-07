package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.ClassListWithTotalCountDto
import edu.watumull.presencify.core.domain.model.schedule.ClassListWithTotalCount

fun ClassListWithTotalCountDto.toDomain(): ClassListWithTotalCount = ClassListWithTotalCount(
    classes = classes.map { it.toDomain() },
    totalCount = totalCount
)
