package edu.watumull.presencify.navigation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun calculateWindowWidth(): Dp {
    val windowInfo = LocalWindowInfo.current
    return windowInfo.containerSize.width.dp
}

