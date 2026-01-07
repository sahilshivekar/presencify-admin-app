package edu.watumull.presencify.core.domain.model.student

import edu.watumull.presencify.core.domain.model.academics.Semester
import kotlinx.datetime.LocalDate

data class StudentSemester(
    val id: String,
    val studentId: String,
    val semesterId: String,
    val fromDate: LocalDate,
    val toDate: LocalDate?,
    val student: Student? = null,
    val semester: Semester? = null
)