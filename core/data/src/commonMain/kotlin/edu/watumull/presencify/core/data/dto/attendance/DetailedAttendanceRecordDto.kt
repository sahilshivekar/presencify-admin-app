package edu.watumull.presencify.core.data.dto.attendance

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DetailedAttendanceRecordDto(
    val attendanceId: Int,
    val date: LocalDate,
    val attendanceStatus: Boolean
)
