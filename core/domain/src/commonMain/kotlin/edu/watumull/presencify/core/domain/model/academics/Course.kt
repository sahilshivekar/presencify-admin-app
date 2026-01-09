package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import edu.watumull.presencify.core.domain.model.teacher.TeacherTeachesCourse

data class Course(
    val id: String,
    val schemeId: String,
    val code: String,
    val name: String,
    val optionalSubject: String? = null,
    val scheme: Scheme? = null,
    val branchCourseSemesters: List<BranchCourseSemester>? = null,
    val classes: List<ClassSession>? = null,
    val teacherTeachesCourses: List<TeacherTeachesCourse>? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = "$code - $name"
}
