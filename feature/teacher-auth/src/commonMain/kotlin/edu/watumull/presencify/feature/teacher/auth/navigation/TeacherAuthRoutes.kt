package edu.watumull.presencify.feature.teacher.auth.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface TeacherAuthRoutes : NavRoute {

    @Serializable
    data object TeacherLogin : TeacherAuthRoutes

    @Serializable
    data object TeacherForgotPassword : TeacherAuthRoutes

    @Serializable
    data class TeacherVerifyCode(val email: String) : TeacherAuthRoutes

}
