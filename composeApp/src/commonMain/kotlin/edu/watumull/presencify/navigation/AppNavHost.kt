package edu.watumull.presencify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.watumull.presencify.core.presentation.navigation.NavRoute
import edu.watumull.presencify.feature.academics.navigation.academicsNavGraph
import edu.watumull.presencify.feature.admin.auth.navigation.adminAuthNavGraph
import edu.watumull.presencify.feature.admin.mgt.navigation.adminMgtNavGraph
import edu.watumull.presencify.feature.attendance.navigation.attendanceNavGraph
import edu.watumull.presencify.feature.onboarding.navigation.onboardingNavGraph
import edu.watumull.presencify.feature.schedule.navigation.scheduleNavGraph
import edu.watumull.presencify.feature.student.auth.navigation.studentAuthNavGraph
import edu.watumull.presencify.feature.teacher.auth.navigation.teacherAuthNavGraph
import edu.watumull.presencify.feature.users.navigation.usersNavGraph
import edu.watumull.presencify.navigation.home.Home
import edu.watumull.presencify.navigation.home.navigateToHome
import edu.watumull.presencify.navigation.navcontroller_extensions.*

@Composable
fun AppNavHost(
    startDestination: NavRoute,
) {
    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {
        composable<Home> {
            Home(rootNavController = rootNavController)
        }

        academicsNavGraph(
            onNavigateBack = { rootNavController.navigateUp() },
            onNavigateToBranchDetails = rootNavController::navigateToBranchDetails,
            onNavigateToAddEditBranch = rootNavController::navigateToAddEditBranch,
            onNavigateToSchemeDetails = rootNavController::navigateToSchemeDetails,
            onNavigateToAddEditScheme = rootNavController::navigateToAddEditScheme,
            onNavigateToCourseDetails = rootNavController::navigateToCourseDetails,
            onNavigateToAddEditCourse = rootNavController::navigateToAddEditCourse,
            onNavigateToSemesterDetails = rootNavController::navigateToSemesterDetails,
            onNavigateToAddEditSemester = rootNavController::navigateToAddEditSemester,
            onNavigateToDivisionDetails = rootNavController::navigateToDivisionDetails,
            onNavigateToAddEditDivision = rootNavController::navigateToAddEditDivision,
            onNavigateToBatchDetails = rootNavController::navigateToBatchDetails,
            onNavigateToAddEditBatch = rootNavController::navigateToAddEditBatch,
        )

        onboardingNavGraph(
            navigateToStudentLogin = {
                rootNavController.navigateToStudentLogin()
            },
            navigateToTeacherLogin = {
                rootNavController.navigateToTeacherLogin()
            },
            navigateToAdminLogin = {
                rootNavController.navigateToAdminLogin()
            }
        )

        attendanceNavGraph()

        usersNavGraph(
            onNavigateBack = { rootNavController.navigateUp() },
            onNavigateToStudentDetails = rootNavController::navigateToStudentDetails,
            onNavigateToAddEditStudent = rootNavController::navigateToAddEditStudent,
            onNavigateToTeacherDetails = rootNavController::navigateToTeacherDetails,
            onNavigateToAddEditTeacher = rootNavController::navigateToAddEditTeacher,
        )

        scheduleNavGraph(
            onNavigateBack = { rootNavController.navigateUp() },
            onNavigateToClassDetails = rootNavController::navigateToClassDetails,
            onNavigateToAddEditClass = rootNavController::navigateToAddEditClass
        )

        adminAuthNavGraph(
            onNavigateToHome = rootNavController::navigateToHome,
            onNavigateToForgotPassword = rootNavController::navigateToAdminForgotPassword,
            onNavigateBack = { rootNavController.navigateUp() },
            onNavigateToVerifyCode = rootNavController::navigateToAdminVerifyCode,
        )

        adminMgtNavGraph(
            onNavigateToUpdatePassword = rootNavController::navigateToUpdateAdminPassword,
            onNavigateBack = { rootNavController.navigateUp() },
            onNavigateToVerifyCode = rootNavController::navigateToAdminVerifyCode,
            onNavigateToAddAdmin = rootNavController::navigateToAddAdmin,
            onNavigateToAdminDetails = rootNavController::navigateToAdminDetails,
        )

        studentAuthNavGraph()

        teacherAuthNavGraph()

    }
}
