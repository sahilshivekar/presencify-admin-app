package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.TimetableListWithTotalCountDto
import edu.watumull.presencify.core.domain.model.schedule.TimetableListWithTotalCount

fun TimetableListWithTotalCountDto.toDomain(): TimetableListWithTotalCount = TimetableListWithTotalCount(
    timetables = timetables.map { it.toDomain() },
    totalCount = totalCount
)
