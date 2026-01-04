package edu.watumull.presencify.core.data.mapper.auth

import edu.watumull.presencify.core.data.dto.auth.LoginAdminDto
import edu.watumull.presencify.core.data.mapper.admin.toDomain
import edu.watumull.presencify.core.domain.model.auth.LoginAdmin

fun LoginAdminDto.toDomain(): LoginAdmin {
    return LoginAdmin(
        admin = admin.toDomain(),
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}