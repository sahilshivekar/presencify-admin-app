package edu.watumull.presencify.core.presentation.bottom_bar

import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavBarItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

val bottomNavBarItems = listOf(
    BottomNavBarItem(
        icon = androidx.compose.material.icons.Icons.Default.Person,
        label = "Users",
        route = "users"
    ),
    BottomNavBarItem(
        icon = androidx.compose.material.icons.Icons.Default.Schedule,
        label = "Schedule",
        route = "schedule"
    ),
    BottomNavBarItem(
        icon = androidx.compose.material.icons.Icons.Default.School,
        label = "Academics",
        route = "academics"
    )
)