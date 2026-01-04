package edu.watumull.presencify.core.domain.model.auth

import edu.watumull.presencify.core.domain.model.student.Student

data class LoginStudent(
    val student: Student,
    val accessToken: String,
    val refreshToken: String
)