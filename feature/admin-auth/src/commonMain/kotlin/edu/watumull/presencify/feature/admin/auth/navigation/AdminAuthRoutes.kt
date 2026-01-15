 package edu.watumull.presencify.feature.admin.auth.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface AdminAuthRoutes : NavRoute {

    @Serializable
    data object AdminLogin : AdminAuthRoutes

    @Serializable
    data object AdminForgotPassword : AdminAuthRoutes

    @Serializable
    data class AdminVerifyCode(val email: String) : AdminAuthRoutes

}
