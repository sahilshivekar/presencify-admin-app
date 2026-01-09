package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.student.StudentSemester
import kotlinx.datetime.LocalDate

data class Semester(
    val id: String,
    val branchId: String,
    val semesterNumber: SemesterNumber,
    val academicStartYear: Int,
    val academicEndYear: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val schemeId: String,
    val branch: Branch? = null,
    val scheme: Scheme? = null,
    val divisions: List<Division>? = null,
    val studentSemesters: List<StudentSemester>? = null,
    val courses: List<Course>? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = "${semesterNumber.toDisplayLabel()} ($academicStartYear-$academicEndYear)"
}
