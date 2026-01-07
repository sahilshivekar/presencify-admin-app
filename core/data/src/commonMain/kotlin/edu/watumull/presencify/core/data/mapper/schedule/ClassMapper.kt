package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.mapper.attendance.toDomain
import edu.watumull.presencify.core.data.mapper.teacher.toDomain
import edu.watumull.presencify.core.domain.model.schedule.ClassSession

fun ClassDto.toDomain(): ClassSession = ClassSession(
    id = id,
    timetableId = timetableId,
    batchId = batchId,
    roomId = roomId,
    courseId = courseId,
    teacherId = teacherId,
    startTime = startTime,
    endTime = endTime,
    dayOfWeek = dayOfWeek,
    activeFrom = activeFrom,
    activeTill = activeTill,
    classType = classType,
    isExtraClass = isExtraClass,
    timetable = timetable?.toDomain(),
    batch = batch?.toDomain(),
    room = room?.toDomain(),
    course = course?.toDomain(),
    teacher = teacher?.toDomain(),
    attendances = attendances?.map { it.toDomain() }
)
