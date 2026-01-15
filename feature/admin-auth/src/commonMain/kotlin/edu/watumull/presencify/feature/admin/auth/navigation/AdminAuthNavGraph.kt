package edu.watumull.presencify.feature.admin.auth.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.adminAuthNavGraph() {

    composableWithSlideTransitions<AdminAuthRoutes.AdminLogin> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AdminAuthRoutes.AdminForgotPassword> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AdminAuthRoutes.AdminVerifyCode> {
        // TODO: Add screen content
    }
}

