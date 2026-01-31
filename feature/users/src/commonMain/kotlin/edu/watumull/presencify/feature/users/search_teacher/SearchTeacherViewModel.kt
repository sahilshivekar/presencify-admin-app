package edu.watumull.presencify.feature.users.search_teacher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository
import edu.watumull.presencify.core.presentation.pagination.Paginator
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.users.navigation.SearchTeacherIntention
import edu.watumull.presencify.feature.users.navigation.UsersRoutes
import edu.watumull.presencify.feature.users.search_teacher.SearchTeacherEvent.NavigateToStaffDetails
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchTeacherViewModel(
    private val teacherRepository: TeacherRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchTeacherState, SearchTeacherEvent, SearchTeacherAction>(
    initialState = SearchTeacherState()
) {

    private val routeParams = savedStateHandle.toRoute<UsersRoutes.SearchTeacher>()

    private val paginator = Paginator<Int, edu.watumull.presencify.core.domain.model.teacher.TeacherListWithTotalCount>(
        initialKey = 1,
        onLoadUpdated = { isLoading ->
            updateState { it.copy(isLoadingMore = isLoading) }
        },
        onRequest = { page ->
            val state = stateFlow.value

            teacherRepository.getTeachers(
                searchQuery = state.searchQuery.ifBlank { null },
                courseId = state.courseId,
                page = page,
                limit = 20,
                getAll = state.getAll
            )
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { error ->
            updateState {
                it.copy(
                    dialogState = SearchTeacherState.DialogState(
                        dialogType = DialogType.ERROR,
                        title = "Error",
                        message = error.toUiText(),
                        dialogIntention = DialogIntention.GENERIC
                    )
                )
            }
        },
        onSuccess = { response, _ ->
            updateState {
                it.copy(
                    teachers = if (stateFlow.value.currentPage == 1) response.teachers.toPersistentList() else it.teachers.addAll(
                        response.teachers.toPersistentList()
                    ),
                    currentPage = stateFlow.value.currentPage + 1,
                    isRefreshing = false,
                    isLoadingTeachers = false
                )
            }
        },
        endReached = { currentPage, response ->
            // End reached when we have loaded all teachers
            val totalLoadedTeachers = currentPage * 20
            totalLoadedTeachers >= response.totalTeacher
        }
    )

    init {
        // Set intention from route params
        val intention = try {
            SearchTeacherIntention.valueOf(routeParams.intention)
        } catch (_: IllegalArgumentException) {
            SearchTeacherIntention.DEFAULT
        }

        // Set intention based on courseId
        val finalIntention = if (routeParams.courseId != null) {
            SearchTeacherIntention.SELECT_TEACHER
        } else {
            intention
        }

        val isSelectable = finalIntention != SearchTeacherIntention.DEFAULT

        updateState {
            it.copy(
                intention = finalIntention,
                isSelectable = isSelectable,
                searchQuery = routeParams.searchQuery ?: "",
                courseId = routeParams.courseId,
                getAll = routeParams.getAll
            )
        }

        // Setup debounced search
        setupDebouncedSearch()
    }

    @OptIn(FlowPreview::class)
    private fun setupDebouncedSearch() {
        viewModelScope.launch {
            stateFlow
                .map { it.searchQuery }
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { _ ->
                    refreshSearch()
                }
        }
    }

    private fun loadNextTeachers() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun refreshSearch() {
        updateState { it.copy(teachers = persistentListOf(), currentPage = 1) }
        paginator.reset()
        loadNextTeachers()
    }

    override fun handleAction(action: SearchTeacherAction) {
        when (action) {

            is SearchTeacherAction.BackButtonClick -> {
                sendEvent(SearchTeacherEvent.NavigateBack)
            }

            is SearchTeacherAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }

            is SearchTeacherAction.UpdateSearchQuery -> {
                updateState { it.copy(searchQuery = action.query) }
            }

            is SearchTeacherAction.Search -> {
                refreshSearch()
            }

            is SearchTeacherAction.Refresh -> {
                updateState { it.copy(isRefreshing = true) }
                refreshSearch()
            }

            is SearchTeacherAction.ToggleTeacherSelection -> {
                val currentSelections = stateFlow.value.selectedTeacherIds
                val newSelections = if (currentSelections.contains(action.teacherId)) {
                    currentSelections - action.teacherId
                } else {
                    currentSelections + action.teacherId
                }
                updateState { it.copy(selectedTeacherIds = newSelections) }
            }

            is SearchTeacherAction.TeacherCardClick -> {
                if (stateFlow.value.isSelectable) {
                    // In selection mode, toggle selection
                    val currentSelections = stateFlow.value.selectedTeacherIds
                    val newSelections = if (currentSelections.contains(action.teacherId)) {
                        currentSelections - action.teacherId
                    } else {
                        currentSelections + action.teacherId
                    }
                    updateState { it.copy(selectedTeacherIds = newSelections) }
                } else {
                    // Navigate to staff details
                    sendEvent(NavigateToStaffDetails(action.teacherId))
                }
            }

            is SearchTeacherAction.LoadMoreTeachers -> {
                loadNextTeachers()
            }

            is SearchTeacherAction.DoneButtonClick -> {
                sendEvent(SearchTeacherEvent.NavigateBackWithSelection)
            }

            is SearchTeacherAction.ClickFloatingActionButton -> {
                sendEvent(SearchTeacherEvent.NavigateToAddEditStaff)
            }
        }
    }
}

