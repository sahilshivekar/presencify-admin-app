package edu.watumull.presencify.core.data.mapper.attendance

import edu.watumull.presencify.core.data.dto.attendance.AttendanceDto
import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.domain.model.attendance.Attendance

fun AttendanceDto.toDomain(): Attendance = Attendance(
    id = id,
    classId = classId,
    date = date,
    totalStudents = totalStudents,
    presentCount = presentCount,
    absentCount = absentCount,
    klass = klass?.toDomain(),
    attendanceStudents = attendanceStudents?.map { it.toDomain() }
)
