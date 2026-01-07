package edu.watumull.presencify.core.domain.model.student

import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.domain.model.attendance.AttendanceStudent

data class Student(
    val id: String,
    val name: String,
    val gender: Gender,
    val email: String,
    val phone: String,
    val prn: String,
    val password: String?,
    val parentPhone: String?,
    val rollNo: Int?,
    val dob: String?,
    val admissionYear: Int?,
    val address: String?,
    val city: String?,
    val state: String?,
    val pincode: String?,
    val branchId: String,
    val schemeId: String,
    val branch: Branch? = null,
    val scheme: Scheme? = null,
    val studentSemesters: List<StudentSemester>? = null,
    val studentDivisions: List<StudentDivision>? = null,
    val studentBatches: List<StudentBatch>? = null,
    val attendanceStudents: List<AttendanceStudent>? = null
)
