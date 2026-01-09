package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.model.schedule.Timetable
import edu.watumull.presencify.core.domain.model.student.StudentDivision

data class Division(
    val id: String,
    val divisionCode: String,
    val semesterId: String,
    val semester: Semester? = null,
    val batches: List<Batch>? = null,
    val studentDivisions: List<StudentDivision>? = null,
    val timetable: Timetable? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = divisionCode
}
