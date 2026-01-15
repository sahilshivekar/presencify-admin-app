package edu.watumull.presencify.feature.teacher.auth.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.teacherAuthNavGraph() {

    composableWithSlideTransitions<TeacherAuthRoutes.TeacherLogin> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<TeacherAuthRoutes.TeacherForgotPassword> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<TeacherAuthRoutes.TeacherVerifyCode> {
        // TODO: Add screen content
    }

}
