package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.enums.SemesterNumber

data class BranchCourseSemester(
    val id: String,
    val branchId: String,
    val courseId: String,
    val semesterNumber: SemesterNumber,
    val branch: Branch? = null,
    val course: Course? = null
)
