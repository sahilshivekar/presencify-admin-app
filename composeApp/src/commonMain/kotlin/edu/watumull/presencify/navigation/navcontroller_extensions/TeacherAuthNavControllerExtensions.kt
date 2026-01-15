package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.teacher.auth.navigation.TeacherAuthRoutes

/**
 * Navigate to Teacher Login screen
 */
fun NavController.navigateToTeacherLogin() {
    navigate(TeacherAuthRoutes.TeacherLogin)
}

/**
 * Navigate to Teacher Forgot Password screen
 */
fun NavController.navigateToTeacherForgotPassword() {
    navigate(TeacherAuthRoutes.TeacherForgotPassword)
}

/**
 * Navigate to Teacher Verify Code screen
 *
 * @param email The email address for verification
 */
fun NavController.navigateToTeacherVerifyCode(email: String) {
    navigate(TeacherAuthRoutes.TeacherVerifyCode(email = email))
}

