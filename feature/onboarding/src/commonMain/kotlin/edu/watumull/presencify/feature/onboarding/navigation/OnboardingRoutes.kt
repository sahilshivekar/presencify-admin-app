package edu.watumull.presencify.feature.onboarding.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface OnboardingRoutes : NavRoute {

    @Serializable
    data object SelectRole : OnboardingRoutes

}
