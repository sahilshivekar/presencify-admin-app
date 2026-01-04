package edu.watumull.presencify.core.domain.model.auth

import edu.watumull.presencify.core.domain.model.teacher.Teacher

data class LoginTeacher(
    val teacher: Teacher,
    val accessToken: String,
    val refreshToken: String
)