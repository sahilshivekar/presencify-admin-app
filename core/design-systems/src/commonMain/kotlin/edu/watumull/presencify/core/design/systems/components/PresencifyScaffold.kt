package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Simple top bar with centered title and left-aligned back button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PresencifyTopBar(
    topBarTitle: String,
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = topBarTitle,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        navigationIcon = {
            IconButton(onClick = backPress) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        actions = actions,
        modifier = modifier,
    )
}

/**
 * Normal scaffold with centered title top app bar and optional floating action button.
 * Used for regular screens that don't need bottom sheets or pull-to-refresh.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresencifyScaffold(
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    topBarTitle: String? = null,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            topBarTitle?.let {
                PresencifyTopBar(
                    topBarTitle = it,
                    backPress = backPress,
                    actions = actions
                )
            }
        },
        floatingActionButton = floatingActionButton,
        containerColor = Color.Transparent,
        content = content
    )
}

/**
 * Bottom sheet scaffold with centered title top app bar, optional floating action button,
 * and bottom sheet. Used for search screens with filtering.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresencifyBottomSheetScaffold(
    sheetContent: @Composable ColumnScope.() -> Unit,
    backPress: () -> Unit,
    modifier: Modifier = Modifier,
    topBarTitle: String? = null,
    floatingActionButton: @Composable () -> Unit = {},
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = SheetValue.Hidden
        )
    ),
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    LaunchedEffect(Unit) {
        scaffoldState.bottomSheetState.hide()
    }
    BottomSheetScaffold(
        sheetContent = sheetContent,
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = sheetShape,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface),
        sheetTonalElevation = 0.dp,
        sheetShadowElevation = BottomSheetDefaults.Elevation,
        topBar = {
            topBarTitle?.let {
                PresencifyTopBar(
                    topBarTitle = it,
                    backPress = backPress,
                    actions = actions
                )
            }
        },
        floatingActionButton = floatingActionButton,
        containerColor = Color.Transparent,
        content = content
    )
}