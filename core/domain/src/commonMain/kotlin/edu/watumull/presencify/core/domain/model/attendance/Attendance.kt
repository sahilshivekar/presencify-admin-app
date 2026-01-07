package edu.watumull.presencify.core.domain.model.attendance

import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import kotlinx.datetime.LocalDate

data class Attendance(
    val id: String,
    val classId: String,
    val date: LocalDate,
    val totalStudents: Int,
    val presentCount: Int,
    val absentCount: Int,
    val klass: ClassSession? = null,
    val attendanceStudents: List<AttendanceStudent>? = null
)