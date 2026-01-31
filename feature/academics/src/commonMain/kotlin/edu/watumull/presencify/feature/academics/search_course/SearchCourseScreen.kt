package edu.watumull.presencify.feature.academics.search_course

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
import edu.watumull.presencify.core.presentation.components.CourseListItem
import edu.watumull.presencify.feature.academics.navigation.SearchCourseIntention
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCourseScreen(
    state: SearchCourseState,
    onAction: (SearchCourseAction) -> Unit,
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

    val topBarTitle = when (state.intention) {
        SearchCourseIntention.DEFAULT -> "Search Courses"
        SearchCourseIntention.LINK_UNLINK_COURSE_TO_SEMESTER_NUMBER_BRANCH -> "Link/Unlink Courses"
        SearchCourseIntention.ASSIGN_UNASSIGN_COURSE_TO_TEACHER -> "Assign/Unassign Courses"
    }

    PresencifyBottomSheetScaffold(
        backPress = { onAction(SearchCourseAction.BackButtonClick) },
        topBarTitle = topBarTitle,
        scaffoldState = scaffoldState,
        sheetContent = {
            SearchCourseBottomSheetContent(
                state = state,
                onAction = onAction,
                onDismiss = {
                    scope.launch { scaffoldState.bottomSheetState.hide() }
                    onAction(SearchCourseAction.HideBottomSheet)
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAction(SearchCourseAction.ClickFloatingActionButton) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add course"
                )
            }
        }
    ) { paddingValues ->
        when (state.viewState) {
            is SearchCourseState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is SearchCourseState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is SearchCourseState.ViewState.Content -> {
                SearchCourseScreenContent(
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
                onAction(SearchCourseAction.DismissDialog)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SearchCourseScreenContent(
    state: SearchCourseState,
    onAction: (SearchCourseAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefreshing,
        onRefresh = { onAction(SearchCourseAction.Refresh) }
    )
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        snapshotFlow {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.distinctUntilChanged().collect { lastVisibleIndex ->
            // Trigger load more when we're close to the end (within 3 items)
            // This accounts for the loading indicator item that comes after courses
            if (lastVisibleIndex != null && lastVisibleIndex >= state.courses.lastIndex - 3) {
                onAction(SearchCourseAction.LoadMoreCourses)
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
                onQueryChange = { onAction(SearchCourseAction.UpdateSearchQuery(it)) },
                onFilterClick = { onAction(SearchCourseAction.ShowBottomSheet) },
                placeholder = "Search courses...",
                onSearchClick = { onAction(SearchCourseAction.Search) }
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
                        items = state.courses,
                        key = { it.id }
                    ) { course ->
                        val schemeName = course.scheme?.name ?: "N/A"

                        CourseListItem(
                            name = course.name,
                            code = course.code,
                            schemeName = schemeName,
                            optionalCourse = course.optionalCourse,
                            trailingContent = if (state.intention != SearchCourseIntention.DEFAULT) {
                                val isLoading = state.loadingCourseIds.contains(course.id)

                                when (state.intention) {
                                    SearchCourseIntention.LINK_UNLINK_COURSE_TO_SEMESTER_NUMBER_BRANCH -> {
                                        // Check if course is linked to the branch+semester combination
                                        val isLinked = state.branchId?.let { bId ->
                                            state.semesterNumber?.let { semNum ->
                                                val semesterEnum = edu.watumull.presencify.core.domain.enums.SemesterNumber.fromValue(semNum)
                                                course.branchCourseSemesters?.any {
                                                    it.branchId == bId && it.semesterNumber == semesterEnum
                                                } == true
                                            }
                                        } ?: false
                                        {
                                            Column(
                                                verticalArrangement = Arrangement.Top,
                                                horizontalAlignment = Alignment.End,
                                            ) {
                                                Text(
                                                    text = if (isLinked) "Linked" else "Not Linked",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = if (isLinked)
                                                        MaterialTheme.colorScheme.primary
                                                    else
                                                        MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Switch(
                                                    checked = isLinked,
                                                    onCheckedChange = {
                                                        onAction(SearchCourseAction.CourseActionButtonClick(course.id))
                                                    },
                                                    enabled = !isLoading,
                                                    thumbContent = if (isLoading) {
                                                        {
                                                            CircularProgressIndicator(
                                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                                                strokeWidth = 2.dp
                                                            )
                                                        }
                                                    } else null
                                                )
                                            }
                                        }
                                    }

                                    SearchCourseIntention.ASSIGN_UNASSIGN_COURSE_TO_TEACHER -> {
                                        // Check if course is assigned to the teacher
                                        val isAssigned = state.teacherId?.let { tId ->
                                            course.teacherTeachesCourses?.any { it.teacherId == tId } == true
                                        } ?: false
                                        {
                                            Column(
                                                verticalArrangement = Arrangement.Top,
                                                horizontalAlignment = Alignment.End,
                                            ) {
                                                Text(
                                                    text = if (isAssigned) "Assigned" else "Not Assigned",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = if (isAssigned)
                                                        MaterialTheme.colorScheme.primary
                                                    else
                                                        MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                                Switch(
                                                    checked = isAssigned,
                                                    onCheckedChange = {
                                                        onAction(SearchCourseAction.CourseActionButtonClick(course.id))
                                                    },
                                                    enabled = !isLoading,
                                                    thumbContent = if (isLoading) {
                                                        {
                                                            CircularProgressIndicator(
                                                                modifier = Modifier.size(
                                                                    SwitchDefaults.IconSize
                                                                ),
                                                                strokeWidth = 1.dp,
                                                                color = MaterialTheme.colorScheme.primary
                                                            )
                                                        }
                                                    } else null,
                                                    colors = SwitchDefaults.colors(
                                                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                                                        uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                                                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                                                        uncheckedBorderColor = MaterialTheme.colorScheme.outline
                                                    )
                                                )
                                            }
                                        }
                                    }

                                    SearchCourseIntention.DEFAULT -> null
                                }
                            } else null,
                            onClick = { onAction(SearchCourseAction.CourseCardClick(course.id)) },
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

                            state.courses.isEmpty() && state.isLoadingCourses -> {
                                PresencifyDefaultLoadingScreen()
                            }

                            state.courses.isEmpty() && !state.isLoadingCourses -> {
                                PresencifyNoResultsIndicator(
                                    text = "No courses found"
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
