package edu.watumull.presencify.core.data.mapper.auth

import edu.watumull.presencify.core.data.dto.auth.SendVerificationCodeDto
import edu.watumull.presencify.core.domain.model.auth.SendVerificationCode

fun SendVerificationCodeDto.toDomain(): SendVerificationCode {
    return SendVerificationCode(
        expiresAt = expiresAt
    )
}