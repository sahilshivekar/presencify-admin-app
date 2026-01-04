package edu.watumull.presencify.core.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)