package edu.watumull.presencify.core.data.mapper.auth

import edu.watumull.presencify.core.data.dto.auth.LoginStudentDto
import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.domain.model.auth.LoginStudent

fun LoginStudentDto.toDomain(): LoginStudent {
    return LoginStudent(
        student = student.toDomain(),
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}