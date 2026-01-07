package edu.watumull.presencify.core.data.dto.teacher

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.TeacherRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherDto(
    val id: String,
    val firstName: String,
    val middleName: String? = null,
    val lastName: String,
    val teacherImageUrl: String? = null,
    val teacherImagePublicId: String? = null,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val highestQualification: String? = null,
    val role: TeacherRole,
    val password: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val isActive: Boolean = true,
    val refreshToken: String? = null,
    @SerialName("Classes")
    val classes: List<ClassDto>? = null,
    @SerialName("TeacherTeachesCourses")
    val teacherTeachesCourses: List<TeacherTeachesCourseDto>? = null
)
