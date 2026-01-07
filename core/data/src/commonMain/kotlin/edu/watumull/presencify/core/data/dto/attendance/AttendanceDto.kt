package edu.watumull.presencify.core.data.dto.attendance

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceDto(
    val id: String,
    val classId: String,
    val date: LocalDate,
    val totalStudents: Int,
    val presentCount: Int,
    val absentCount: Int,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Class")
    val klass: ClassDto? = null,
    @SerialName("AttendanceStudents")
    val attendanceStudents: List<AttendanceStudentDto>? = null
)
