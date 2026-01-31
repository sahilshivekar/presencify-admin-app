package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = actions,
        modifier = modifier,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
            canScroll = {
                false
            }
        ),
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
    floatingActionButton: (@Composable () -> Unit)? = null,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = SheetValue.Hidden
        )
    ),
    sheetPeekHeight: Dp = 0.dp,
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
        containerColor = Color.Transparent,
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content(paddingValues)
                floatingActionButton?.let {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        floatingActionButton()
                    }
                }
            }
        }
    )
}