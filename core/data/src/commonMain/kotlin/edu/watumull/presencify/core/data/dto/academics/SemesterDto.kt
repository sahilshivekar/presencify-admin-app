package edu.watumull.presencify.core.data.dto.academics

import edu.watumull.presencify.core.data.dto.student.StudentSemesterDto
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SemesterDto(
    val id: String,
    val branchId: String,
    val semesterNumber: SemesterNumber,
    val academicStartYear: Int,
    val academicEndYear: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val schemeId: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    @SerialName("Branch")
    val branch: BranchDto? = null,
    @SerialName("Scheme")
    val scheme: SchemeDto? = null,
    @SerialName("Divisions")
    val divisions: List<DivisionDto>? = null,
    @SerialName("StudentSemesters")
    val studentSemesters: List<StudentSemesterDto>? = null,
    @SerialName("Courses")
    val courses: List<CourseDto>? = null
)
