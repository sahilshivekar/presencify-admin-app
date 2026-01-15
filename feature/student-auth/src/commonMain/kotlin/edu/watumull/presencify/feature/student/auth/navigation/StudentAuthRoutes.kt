package edu.watumull.presencify.feature.student.auth.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface StudentAuthRoutes : NavRoute {

    @Serializable
    data object StudentLogin : StudentAuthRoutes

    @Serializable
    data object StudentForgotPassword : StudentAuthRoutes

    @Serializable
    data class StudentVerifyCode(val email: String) : StudentAuthRoutes

}
