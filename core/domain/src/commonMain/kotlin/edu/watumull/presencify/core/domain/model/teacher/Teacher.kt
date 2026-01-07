package edu.watumull.presencify.core.domain.model.teacher

import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.TeacherRole
import edu.watumull.presencify.core.domain.model.schedule.ClassSession

data class Teacher(
    val id: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val teacherImageUrl: String?,
    val teacherImagePublicId: String?,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val highestQualification: String?,
    val role: TeacherRole,
    val password: String?,
    val isActive: Boolean,
    val refreshToken: String?,
    val classes: List<ClassSession>? = null,
    val teacherTeachesCourses: List<TeacherTeachesCourse>? = null
)
