package edu.watumull.presencify.core.data.dto.attendance

import edu.watumull.presencify.core.data.dto.student.StudentDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceStudentDto(
    val id: String,
    val attendanceId: String,
    val studentId: String,
    val attendanceStatus: Boolean,
    val markedAt: String? = null,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Attendance")
    val attendance: AttendanceDto? = null,
    @SerialName("Student")
    val student: StudentDto? = null
)
