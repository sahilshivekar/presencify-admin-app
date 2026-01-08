package edu.watumull.presencify.core.data.mapper.attendance

import edu.watumull.presencify.core.data.dto.attendance.AttendanceStudentDto
import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.domain.model.attendance.AttendanceStudent

fun AttendanceStudentDto.toDomain(): AttendanceStudent = AttendanceStudent(
    id = id,
    attendanceId = attendanceId,
    studentId = studentId,
    attendanceStatus = attendanceStatus,
    markedAt = markedAt,
    attendance = attendance?.toDomain(),
    student = student?.toDomain()
)
