package edu.watumull.presencify.navigation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import edu.watumull.presencify.feature.attendance.navigation.AttendanceRoutes
import edu.watumull.presencify.feature.schedule.navigation.ScheduleRoutes
import edu.watumull.presencify.feature.users.navigation.UsersRoutes

@Composable
fun HomeNavigationRail(
    currentDestination: NavDestination?,
    onItemSelected: (Any) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Spacer(Modifier.height(16.dp))

        bottomNavBarItems.forEach { item ->
            val isSelected = when (item.route) {
                AttendanceRoutes.AttendanceDashboard -> currentDestination?.hasRoute<AttendanceRoutes.AttendanceDashboard>() ?: false
                ScheduleRoutes.ScheduleDashboard -> currentDestination?.hasRoute<ScheduleRoutes.ScheduleDashboard>() ?: false
                UsersRoutes.UsersDashboard -> currentDestination?.hasRoute<UsersRoutes.UsersDashboard>() ?: false
                AcademicsRoutes.AcademicsDashboard -> currentDestination?.hasRoute<AcademicsRoutes.AcademicsDashboard>() ?: false
                else -> false
            }

            NavigationRailItem(
                selected = isSelected,
                onClick = {
                    onItemSelected(item.route)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        maxLines = 1
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

