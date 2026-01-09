package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.model.student.Student

data class Branch(
    val id: String,
    val name: String,
    val abbreviation: String,
    val semesters: List<Semester>? = null,
    val students: List<Student>? = null,
    val branchCourseSemesters: List<BranchCourseSemester>? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = abbreviation
}
