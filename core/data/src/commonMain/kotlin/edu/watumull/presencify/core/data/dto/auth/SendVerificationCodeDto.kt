package edu.watumull.presencify.core.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class SendVerificationCodeDto(
    val expiresAt: String
)