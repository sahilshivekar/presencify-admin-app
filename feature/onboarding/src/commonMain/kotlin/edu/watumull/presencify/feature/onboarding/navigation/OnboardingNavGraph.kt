package edu.watumull.presencify.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions
import edu.watumull.presencify.feature.onboarding.select_role.SelectRoleRoot

fun NavGraphBuilder.onboardingNavGraph(
    navigateToStudentLogin: () -> Unit,
    navigateToTeacherLogin: () -> Unit,
    navigateToAdminLogin: () -> Unit,
) {

    composableWithSlideTransitions<OnboardingRoutes.SelectRole> {
        SelectRoleRoot(
            onNavigateToStudentLogin = navigateToStudentLogin,
            onNavigateToTeacherLogin = navigateToTeacherLogin,
            onNavigateToAdminLogin = navigateToAdminLogin
        )
    }
}
