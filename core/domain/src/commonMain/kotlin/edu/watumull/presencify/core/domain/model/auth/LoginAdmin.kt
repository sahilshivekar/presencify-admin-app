package edu.watumull.presencify.core.domain.model.auth

import edu.watumull.presencify.core.domain.model.admin.Admin

data class LoginAdmin(
    val admin: Admin,
    val accessToken: String,
    val refreshToken: String
)