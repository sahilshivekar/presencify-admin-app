package edu.watumull.presencify.core.data.mapper.auth

import edu.watumull.presencify.core.data.dto.auth.LoginTeacherDto
import edu.watumull.presencify.core.data.mapper.teacher.toDomain
import edu.watumull.presencify.core.domain.model.auth.LoginTeacher

fun LoginTeacherDto.toDomain(): LoginTeacher {
    return LoginTeacher(
        teacher = teacher.toDomain(),
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}