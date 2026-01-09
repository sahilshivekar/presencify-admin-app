package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.model.student.Student

data class Scheme(
    val id: String,
    val name: String,
    val universityId: String,
    val university: University? = null,
    val semesters: List<Semester>? = null,
    val students: List<Student>? = null,
    val courses: List<Course>? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = name
}
