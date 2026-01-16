package edu.watumull.presencify.navigation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.outlined.AccountTree
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.HowToReg
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import edu.watumull.presencify.core.presentation.navigation.NavRoute
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import edu.watumull.presencify.feature.attendance.navigation.AttendanceRoutes
import edu.watumull.presencify.feature.schedule.navigation.ScheduleRoutes
import edu.watumull.presencify.feature.users.navigation.UsersRoutes


@Composable
fun HomeBottomNavigationBar(
    currentDestination: NavDestination?,
    onItemSelected: (Any) -> Unit
) {

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        bottomNavBarItems.forEach { item ->
            val isSelected = when (item.route) {
                AttendanceRoutes.AttendanceDashboard -> currentDestination?.hasRoute<AttendanceRoutes.AttendanceDashboard>() ?: false
                ScheduleRoutes.ScheduleDashboard -> currentDestination?.hasRoute<ScheduleRoutes.ScheduleDashboard>() ?: false
                UsersRoutes.UsersDashboard -> currentDestination?.hasRoute<UsersRoutes.UsersDashboard>() ?: false
                AcademicsRoutes.AcademicsDashboard -> currentDestination?.hasRoute<AcademicsRoutes.AcademicsDashboard>() ?: false
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onItemSelected(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 9.5.sp,
                        maxLines = 1
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }

}


data class BottomNavBarItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val route: NavRoute
)

val bottomNavBarItems = listOf(
    BottomNavBarItem(
        selectedIcon = Icons.Filled.HowToReg,
        unselectedIcon = Icons.Outlined.HowToReg,
        label = "Attendance",
        route = AttendanceRoutes.AttendanceDashboard
    ),
    BottomNavBarItem(
        selectedIcon = Icons.Filled.Event,
        unselectedIcon = Icons.Outlined.Event,
        label = "Schedule",
        route = ScheduleRoutes.ScheduleDashboard
    ),
    BottomNavBarItem(
        selectedIcon = Icons.Filled.Group,
        unselectedIcon = Icons.Outlined.Group,
        label = "Users",
        route = UsersRoutes.UsersDashboard
    ),
    BottomNavBarItem(
        selectedIcon = Icons.Default.AccountTree,
        unselectedIcon = Icons.Outlined.AccountTree,
        label = "Academics",
        route = AcademicsRoutes.AcademicsDashboard
    ),
)