package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.admin.auth.navigation.AdminAuthRoutes

/**
 * Navigate to Admin Login screen
 */
fun NavController.navigateToAdminLogin() {
    navigate(AdminAuthRoutes.AdminLogin)
}

/**
 * Navigate to Admin Forgot Password screen
 */
fun NavController.navigateToAdminForgotPassword() {
    navigate(AdminAuthRoutes.AdminForgotPassword)
}

/**
 * Navigate to Admin Verify Code screen
 *
 * @param email The email address for verification
 */
fun NavController.navigateToAdminVerifyCode(email: String) {
    navigate(AdminAuthRoutes.AdminVerifyCode(email = email))
}

