package edu.watumull.presencify.feature.academics.search_division

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyBottomSheetScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyDefaultLoadingScreen
import edu.watumull.presencify.core.design.systems.components.PresencifyNoResultsIndicator
import edu.watumull.presencify.core.design.systems.components.PresencifySearchBar
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.components.DivisionListItem
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDivisionScreen(
    state: SearchDivisionState,
    onAction: (SearchDivisionAction) -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false,
            initialValue = SheetValue.Hidden
        )
    )

    // Control bottom sheet visibility based on state
    LaunchedEffect(state.isBottomSheetVisible) {
        if (state.isBottomSheetVisible) {
            scaffoldState.bottomSheetState.expand()
        }
    }

    val scope = rememberCoroutineScope()
    PresencifyBottomSheetScaffold(
        backPress = { onAction(SearchDivisionAction.BackButtonClick) },
        topBarTitle = "Search Divisions",
        scaffoldState = scaffoldState,
        sheetContent = {
            SearchDivisionBottomSheetContent(
                state = state,
                onAction = onAction,
                onDismiss = {
                    scope.launch { scaffoldState.bottomSheetState.hide() }
                    onAction(SearchDivisionAction.HideBottomSheet)
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(SearchDivisionAction.ClickFloatingActionButton) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add division"
                )
            }
        }
    ) { paddingValues ->
        when (state.viewState) {
            is SearchDivisionState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is SearchDivisionState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is SearchDivisionState.ViewState.Content -> {
                SearchDivisionScreenContent(
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
                onAction(SearchDivisionAction.DismissDialog)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchDivisionScreenContent(
    state: SearchDivisionState,
    onAction: (SearchDivisionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { onAction(SearchDivisionAction.Refresh) }
    )
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.distinctUntilChanged().collect { lastVisibleIndex ->
            // Trigger load more when we're close to the end (within 3 items)
            // This accounts for the loading indicator item that comes after divisions
            if (lastVisibleIndex != null && lastVisibleIndex >= state.divisions.lastIndex - 3) {
                onAction(SearchDivisionAction.LoadMoreDivisions)
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
                onQueryChange = { onAction(SearchDivisionAction.UpdateSearchQuery(it)) },
                onFilterClick = { onAction(SearchDivisionAction.ShowBottomSheet) },
                placeholder = "Search divisions...",
                onSearchClick = { onAction(SearchDivisionAction.Search) }
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.divisions,
                        key = { it.id }
                    ) { division ->
                        val semester = division.semester
                        val branch = semester?.branch
                        val batchCodes = division.batches?.map { it.batchCode }?.toPersistentList()
                            ?: kotlinx.collections.immutable.persistentListOf()

                        if (semester != null && branch != null) {
                            DivisionListItem(
                                divisionCode = division.divisionCode,
                                batchCodes = batchCodes,
                                semesterNumber = semester.semesterNumber,
                                semesterAcademicStartYear = semester.academicStartYear,
                                semesterAcademicEndYear = semester.academicEndYear,
                                branchAbbreviation = branch.abbreviation,
                                onClick = { onAction(SearchDivisionAction.DivisionCardClick(division.id)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
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

                            state.divisions.isEmpty() && state.isLoadingDivisions -> {
                                PresencifyDefaultLoadingScreen()
                            }

                            state.divisions.isEmpty() && !state.isLoadingDivisions -> {
                                PresencifyNoResultsIndicator(
                                    text = "No divisions found"
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
    }
}

