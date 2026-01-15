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
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavRoute

@Composable
fun HomeNavHost(
    homeNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = homeNavController,
        startDestination = AttendanceRoutes.AttendanceDashboard,
        modifier = modifier
    ) {
        academicsDashboard()
        attendanceDashboard()
        usersDashboard()
        scheduleDashboard()
    }
}
