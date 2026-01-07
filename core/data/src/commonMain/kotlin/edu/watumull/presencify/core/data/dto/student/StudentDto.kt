package edu.watumull.presencify.core.data.dto.student

import edu.watumull.presencify.core.data.dto.academics.BranchDto
import edu.watumull.presencify.core.data.dto.academics.SchemeDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceStudentDto
import edu.watumull.presencify.core.domain.enums.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentDto(
    val id: String,
    val name: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val prn: String,
    val password: String? = null,
    val parentPhone: String? = null,
    val rollNo: Int? = null,
    val dob: String? = null,
    val admissionYear: Int? = null,
    val address: String? = null,
    val city: String? = null,
    val state: String? = null,
    val pincode: String? = null,
    val branchId: String,
    val schemeId: String,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Branch")
    val branch: BranchDto? = null,
    @SerialName("Scheme")
    val scheme: SchemeDto? = null,
    @SerialName("StudentSemesters")
    val studentSemesters: List<StudentSemesterDto>? = null,
    @SerialName("StudentDivisions")
    val studentDivisions: List<StudentDivisionDto>? = null,
    @SerialName("StudentBatches")
    val studentBatches: List<StudentBatchDto>? = null,
    @SerialName("AttendanceStudents")
    val attendanceStudents: List<AttendanceStudentDto>? = null
)
