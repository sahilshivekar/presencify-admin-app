package edu.watumull.presencify.core.data.dto.auth

import edu.watumull.presencify.core.data.dto.student.StudentDto
import kotlinx.serialization.Serializable

@Serializable
data class LoginStudentDto(
    val student: StudentDto,
    val accessToken: String,
    val refreshToken: String
)