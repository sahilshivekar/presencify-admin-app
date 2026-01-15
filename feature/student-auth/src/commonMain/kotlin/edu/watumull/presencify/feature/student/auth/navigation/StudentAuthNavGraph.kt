package edu.watumull.presencify.feature.student.auth.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.studentAuthNavGraph() {

    composableWithSlideTransitions<StudentAuthRoutes.StudentLogin> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<StudentAuthRoutes.StudentForgotPassword> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<StudentAuthRoutes.StudentVerifyCode> {
        // TODO: Add screen content
    }

}

