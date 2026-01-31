package edu.watumull.presencify.core.data.dto.academics

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.dto.teacher.TeacherTeachesCourseDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseDto(
    val id: String,
    val schemeId: String,
    val code: String,
    val name: String,
    val optionalCourse: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    @SerialName("Scheme")
    val scheme: SchemeDto? = null,
    @SerialName("BranchCourseSemesters")
    val branchCourseSemesters: List<BranchCourseSemesterDto>? = null,
    @SerialName("Classes")
    val classes: List<ClassDto>? = null,
    @SerialName("TeacherTeachesCourses")
    val teacherTeachesCourses: List<TeacherTeachesCourseDto>? = null
)
