package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.onboarding.navigation.OnboardingRoutes

/**
 * Navigate to Select Role screen
 */
fun NavController.navigateToSelectRole() {
    navigate(OnboardingRoutes.SelectRole)
}
