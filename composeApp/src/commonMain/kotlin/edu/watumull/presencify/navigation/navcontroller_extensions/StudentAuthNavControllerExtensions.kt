package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.student.auth.navigation.StudentAuthRoutes

/**
 * Navigate to Student Login screen
 */
fun NavController.navigateToStudentLogin() {
    navigate(StudentAuthRoutes.StudentLogin)
}

/**
 * Navigate to Student Forgot Password screen
 */
fun NavController.navigateToStudentForgotPassword() {
    navigate(StudentAuthRoutes.StudentForgotPassword)
}

/**
 * Navigate to Student Verify Code screen
 *
 * @param email The email address for verification
 */
fun NavController.navigateToStudentVerifyCode(email: String) {
    navigate(StudentAuthRoutes.StudentVerifyCode(email = email))
}


