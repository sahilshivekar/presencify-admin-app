package edu.watumull.presencify.core.data.dto.auth

import edu.watumull.presencify.core.data.dto.teacher.TeacherDto
import kotlinx.serialization.Serializable

@Serializable
data class LoginTeacherDto(
    val teacher: TeacherDto,
    val accessToken: String,
    val refreshToken: String
)