package edu.watumull.presencify.core.data.dto.attendance

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceRecordDto(
    val attendanceDate: LocalDate,
    val totalStudents: Int,
    val presentStudents: Int,
    val attendanceId: Int
)
