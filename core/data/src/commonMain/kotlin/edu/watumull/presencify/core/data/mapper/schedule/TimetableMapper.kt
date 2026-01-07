package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.TimetableDto
import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.domain.model.schedule.Timetable

fun TimetableDto.toDomain(): Timetable = Timetable(
    id = id,
    divisionId = divisionId,
    timetableVersion = timetableVersion,
    division = division?.toDomain(),
    classes = classes?.map { it.toDomain() }
)
