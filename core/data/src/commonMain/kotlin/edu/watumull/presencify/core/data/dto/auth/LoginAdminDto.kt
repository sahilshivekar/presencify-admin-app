package edu.watumull.presencify.core.data.dto.auth

import edu.watumull.presencify.core.data.dto.admin.AdminDto
import kotlinx.serialization.Serializable

@Serializable
data class LoginAdminDto(
    val admin: AdminDto,
    val accessToken: String,
    val refreshToken: String
)