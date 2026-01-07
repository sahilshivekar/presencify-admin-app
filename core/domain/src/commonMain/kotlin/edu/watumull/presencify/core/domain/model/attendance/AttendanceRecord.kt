package edu.watumull.presencify.core.domain.model.attendance

import kotlinx.datetime.LocalDate

data class AttendanceRecord(
    val attendanceDate: LocalDate,
    val totalStudents: Int,
    val presentStudents: Int,
    val attendanceId: Int
)
