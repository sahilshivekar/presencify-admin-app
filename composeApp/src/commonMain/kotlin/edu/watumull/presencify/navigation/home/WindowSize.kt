package edu.watumull.presencify.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Window size classes based on Material Design 3 guidelines
 */
enum class WindowWidthSizeClass {
    Compact,  // < 600dp
    Medium,   // 600dp - 840dp
    Expanded  // > 840dp
}

/**
 * Calculate the current window width size class
 */
@Composable
expect fun calculateWindowWidth(): Dp

@Composable
fun calculateWindowWidthSizeClass(): WindowWidthSizeClass {
    val windowWidth = calculateWindowWidth()
    return when {
        windowWidth < 600.dp -> WindowWidthSizeClass.Compact
        windowWidth < 840.dp -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
}

