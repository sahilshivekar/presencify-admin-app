package edu.watumull.presencify.core.data.mapper.academics

import edu.watumull.presencify.core.data.dto.academics.DivisionDto
import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.domain.model.academics.Division

fun DivisionDto.toDomain(): Division = Division(
    id = id,
    divisionCode = divisionCode,
    semesterId = semesterId,
    semester = semester?.toDomain(),
    batches = batches?.map { it.toDomain() },
    studentDivisions = studentDivisions?.map { it.toDomain() },
    timetable = timetable?.toDomain()
)
