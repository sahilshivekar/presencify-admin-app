package edu.watumull.presencify.feature.users.search_teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.*
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.components.TeacherListItem
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTeacherScreen(
    state: SearchTeacherState,
    onAction: (SearchTeacherAction) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = SheetValue.Hidden
        )
    )

    PresencifyBottomSheetScaffold(
        backPress = { onAction(SearchTeacherAction.BackButtonClick) },
        topBarTitle = "Search Teachers",
        scaffoldState = scaffoldState,
        sheetContent = {
            // Empty sheet content since teachers don't have filters
            Box(modifier = Modifier.height(1.dp))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(SearchTeacherAction.ClickFloatingActionButton) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add teacher"
                )
            }
        }
    ) { paddingValues ->
        when (state.viewState) {
            is SearchTeacherState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is SearchTeacherState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is SearchTeacherState.ViewState.Content -> {
                SearchTeacherScreenContent(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }

    state.dialogState?.let { dialogState ->
        PresencifyAlertDialog(
            isVisible = dialogState.isVisible,
            dialogType = dialogState.dialogType,
            title = dialogState.title,
            message = dialogState.message.asString(),
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.GENERIC -> {
                        // Handle generic dialog confirmation
                    }
                }
            },
            onDismiss = {
                onAction(SearchTeacherAction.DismissDialog)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchTeacherScreenContent(
    state: SearchTeacherState,
    onAction: (SearchTeacherAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { onAction(SearchTeacherAction.Refresh) }
    )
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.distinctUntilChanged().collect { lastVisibleIndex ->
            // Trigger load more when we're close to the end (within 3 items)
            // This accounts for the loading indicator item that comes after teachers
            if (lastVisibleIndex != null && lastVisibleIndex >= state.teachers.lastIndex - 3) {
                onAction(SearchTeacherAction.LoadMoreTeachers)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PresencifySearchBar(
                query = state.searchQuery,
                onQueryChange = { onAction(SearchTeacherAction.UpdateSearchQuery(it)) },
                placeholder = "Search teachers...",
                onSearchClick = { onAction(SearchTeacherAction.Search) },
                showFilterIcon = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.teachers,
                        key = { it.id }
                    ) { teacher ->
                        val teacherName =
                            "${teacher.firstName} ${teacher.middleName?.let { teacher.middleName + " " } ?: ""}${teacher.lastName}"
                        TeacherListItem(
                            teacherName = teacherName,
                            role = teacher.role,
                            teacherImageUrl = teacher.teacherImageUrl,
                            trailingContent = if (state.isSelectable) {
                                {
                                    Icon(
                                        imageVector = if (state.selectedTeacherIds.contains(teacher.id)) {
                                            Icons.Filled.CheckCircle
                                        } else {
                                            Icons.Outlined.Circle
                                        },
                                        contentDescription = if (state.selectedTeacherIds.contains(teacher.id)) {
                                            "Selected"
                                        } else {
                                            "Not selected"
                                        },
                                        tint = if (state.selectedTeacherIds.contains(teacher.id)) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            } else null,
                            onClick = { onAction(SearchTeacherAction.TeacherCardClick(teacher.id)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        when {
                            state.isLoadingMore -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(32.dp),
                                        strokeWidth = 2.dp
                                    )
                                }
                            }

                            state.teachers.isEmpty() && state.isLoadingTeachers -> {
                                PresencifyDefaultLoadingScreen()
                            }

                            state.teachers.isEmpty() && !state.isLoadingTeachers -> {
                                PresencifyNoResultsIndicator(
                                    text = "No teachers found"
                                )
                            }
                        }
                    }
                }
                if (state.isRefreshing) {
                    PullRefreshIndicator(
                        refreshing = true,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }


        }

        // Done button for selection mode
        if (state.isSelectable) {
            PresencifyButton(
                onClick = { onAction(SearchTeacherAction.DoneButtonClick) },
                text = "Done (${state.selectedTeacherIds.size} selected)",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

