package edu.watumull.presencify.navigation.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.watumull.presencify.navigation.navcontroller_extensions.navigateToAdminDetails

@Composable
fun Home(
    rootNavController: NavHostController,
) {

    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Determine window size for adaptive navigation
    val windowSizeClass = calculateWindowWidthSizeClass()
    val useNavigationRail = windowSizeClass >= WindowWidthSizeClass.Medium

    Scaffold(
        topBar = {
            HomeTopBar(
                onProfileIconButtonClick = {
                    rootNavController.navigateToAdminDetails()
                },
            )

        },

        bottomBar = {
            // Only show bottom bar on compact screens
            if (!useNavigationRail) {
                HomeBottomNavigationBar(
                    onItemSelected = { route ->
                        homeNavController.navigate(route) {
                            popUpTo(homeNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    currentDestination = currentDestination
                )
            }
        },

        ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalAlignment = Alignment.Top,
        ) {
            // Show navigation rail on medium+ screens
            if (useNavigationRail) {
                HomeNavigationRail(
                    currentDestination = currentDestination,
                    onItemSelected = { route ->
                        homeNavController.navigate(route) {
                            popUpTo(homeNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            HomeNavHost(
                modifier = Modifier.weight(1f),
                homeNavController = homeNavController,
            )
        }
    }
}