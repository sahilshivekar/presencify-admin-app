package edu.watumull.presencify.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import edu.watumull.presencify.core.presentation.navigation.NavRoute
import edu.watumull.presencify.feature.academics.navigation.academicsDashboard
import edu.watumull.presencify.feature.attendance.navigation.AttendanceRoutes
import edu.watumull.presencify.feature.attendance.navigation.attendanceDashboard
import edu.watumull.presencify.feature.schedule.navigation.scheduleDashboard
import edu.watumull.presencify.feature.users.navigation.usersDashboard
import edu.watumull.presencify.navigation.navcontroller_extensions.*
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavRoute

@Composable
fun HomeNavHost(
    homeNavController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = homeNavController,
        startDestination = AttendanceRoutes.AttendanceDashboard,
        modifier = modifier
    ) {
        academicsDashboard(
            onNavigateToSearchBranch = rootNavController::navigateToSearchBranch,
            onNavigateToSearchScheme = rootNavController::navigateToSearchScheme,
            onNavigateToSearchCourse = rootNavController::navigateToSearchCourse,
            onNavigateToLinkUnlinkCourse = rootNavController::navigateToLinkUnlinkCourse,
            onNavigateToUniversityDetails = rootNavController::navigateToUniversityDetails,
            onNavigateToSearchSemester = rootNavController::navigateToSearchSemester,
            onNavigateToSearchDivision = rootNavController::navigateToSearchDivision,
            onNavigateToSearchBatch = rootNavController::navigateToSearchBatch
        )

        attendanceDashboard()

        usersDashboard(
            onNavigateToSearchStudents = rootNavController::navigateToSearchStudent,
            onNavigateToSearchTeachers = rootNavController::navigateToSearchTeacher,
            onNavigateToAssignUnassignSemester = rootNavController::navigateToAssignUnassignStudentToSemester,
            onNavigateToAssignUnassignDivision = rootNavController::navigateToAssignUnassignStudentToDivision,
            onNavigateToAssignUnassignBatch = rootNavController::navigateToAssignUnassignStudentToBatch,
            onNavigateToModifyDivision = rootNavController::navigateToModifyStudentDivision,
            onNavigateToModifyBatch = rootNavController::navigateToModifyStudentBatch,
            onNavigateToAddToDropout = rootNavController::navigateToAddStudentToDropout,
            onNavigateToRemoveFromDropout = rootNavController::navigateToRemoveStudentFromDropout
        )

        scheduleDashboard(
            onNavigateToSearchRoom = rootNavController::navigateToSearchRoom,
            onNavigateToSearchClass = rootNavController::navigateToSearchClass
        )
    }
}

fun NavHostController.navigateToHome() {
    navigate(Home){
        popUpTo(graph.startDestinationId){
            inclusive = true
        }
    }
}

