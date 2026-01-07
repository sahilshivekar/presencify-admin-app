package edu.watumull.presencify.core.data.dto.academics

import edu.watumull.presencify.core.data.dto.schedule.TimetableDto
import edu.watumull.presencify.core.data.dto.student.StudentDivisionDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DivisionDto(
    val id: String,
    val divisionCode: String,
    val semesterId: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Semester")
    val semester: SemesterDto? = null,
    @SerialName("Batches")
    val batches: List<BatchDto>? = null,
    @SerialName("StudentDivisions")
    val studentDivisions: List<StudentDivisionDto>? = null,
    @SerialName("Timetable")
    val timetable: TimetableDto? = null
)
