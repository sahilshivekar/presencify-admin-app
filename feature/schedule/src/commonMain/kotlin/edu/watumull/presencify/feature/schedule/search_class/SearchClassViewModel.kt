package edu.watumull.presencify.feature.schedule.search_class

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.BatchRepository
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository
import edu.watumull.presencify.core.domain.repository.schedule.ClassSessionRepository
import edu.watumull.presencify.core.domain.repository.schedule.RoomRepository
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository
import edu.watumull.presencify.core.presentation.pagination.Paginator
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchClassViewModel(
    private val classSessionRepository: ClassSessionRepository,
    private val teacherRepository: TeacherRepository,
    private val roomRepository: RoomRepository,
    private val batchRepository: BatchRepository,
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchClassState, SearchClassEvent, SearchClassAction>(
    initialState = SearchClassState()
) {

    private val paginator = Paginator<Int, edu.watumull.presencify.core.domain.model.schedule.ClassListWithTotalCount>(
        initialKey = 1,
        onLoadUpdated = { isLoading ->
            updateState { it.copy(isLoadingMore = isLoading) }
        },
        onRequest = { page ->
            val state = stateFlow.value

            classSessionRepository.getClasses(
                searchQuery = state.searchQuery.ifBlank { null },
                timetableId = null,
                teacherId = state.selectedTeacher?.id,
                roomId = state.selectedRoom?.id,
                batchId = state.selectedBatch?.id,
                courseId = state.selectedCourse?.id,
                dayOfWeek = state.selectedDayOfWeek,
                classType = state.selectedClassType,
                isExtraClass = state.isExtraClass,
                page = page,
                limit = 20,
                getAll = false
            )
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { error ->
            updateState {
                it.copy(
                    dialogState = SearchClassState.DialogState(
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
                    classes = if (stateFlow.value.currentPage == 1) response.classes.toPersistentList() else it.classes.addAll(
                        response.classes.toPersistentList()
                    ),
                    currentPage = stateFlow.value.currentPage + 1,
                    isRefreshing = false,
                    isLoadingClasses = false
                )
            }
        },
        endReached = { currentPage, response ->
            val totalLoadedClasses = currentPage * 20
            totalLoadedClasses >= response.totalCount
        }
    )

    init {
        // Load initial data
        viewModelScope.launch {
            loadTeachers()
            loadRooms()
            loadBatches()
            loadCourses()
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

    private fun loadNextClasses() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun refreshSearch() {
        updateState { it.copy(classes = persistentListOf(), currentPage = 1) }
        paginator.reset()
        loadNextClasses()
    }

    private suspend fun loadTeachers() {
        updateState { it.copy(areTeachersLoading = true) }
        teacherRepository.getTeachers(searchQuery = null, getAll = true)
            .onSuccess { teachersWithCount ->
                updateState {
                    it.copy(
                        teacherOptions = teachersWithCount.teachers.toPersistentList(),
                        areTeachersLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areTeachersLoading = false,
                        dialogState = SearchClassState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    private suspend fun loadRooms() {
        updateState { it.copy(areRoomsLoading = true) }
        roomRepository.getRooms(searchQuery = null, getAll = true)
            .onSuccess { rooms ->
                updateState {
                    it.copy(
                        roomOptions = rooms.toPersistentList(),
                        areRoomsLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areRoomsLoading = false,
                        dialogState = SearchClassState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    private suspend fun loadBatches() {
        updateState { it.copy(areBatchesLoading = true) }
        batchRepository.getBatches(searchQuery = null, getAll = true)
            .onSuccess { batchesWithCount ->
                updateState {
                    it.copy(
                        batchOptions = batchesWithCount.batches.toPersistentList(),
                        areBatchesLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areBatchesLoading = false,
                        dialogState = SearchClassState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    private suspend fun loadCourses() {
        updateState { it.copy(areCoursesLoading = true) }
        courseRepository.getCourses(searchQuery = null, getAll = true)
            .onSuccess { coursesWithCount ->
                updateState {
                    it.copy(
                        courseOptions = coursesWithCount.courses.toPersistentList(),
                        areCoursesLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areCoursesLoading = false,
                        dialogState = SearchClassState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    override fun handleAction(action: SearchClassAction) {
        when (action) {
            SearchClassAction.BackButtonClick -> sendEvent(SearchClassEvent.NavigateBack)
            SearchClassAction.DismissDialog -> updateState { it.copy(dialogState = null) }

            SearchClassAction.ShowBottomSheet -> updateState { it.copy(isBottomSheetVisible = true) }
            SearchClassAction.HideBottomSheet -> updateState { it.copy(isBottomSheetVisible = false) }

            is SearchClassAction.UpdateSearchQuery -> updateState { it.copy(searchQuery = action.query) }
            SearchClassAction.Search -> refreshSearch()
            SearchClassAction.Refresh -> {
                updateState { it.copy(isRefreshing = true) }
                refreshSearch()
            }

            is SearchClassAction.SelectTeacher -> {
                updateState { it.copy(selectedTeacher = action.teacher) }
            }
            is SearchClassAction.SelectRoom -> {
                updateState { it.copy(selectedRoom = action.room) }
            }
            is SearchClassAction.SelectBatch -> {
                updateState { it.copy(selectedBatch = action.batch) }
            }
            is SearchClassAction.SelectCourse -> {
                updateState { it.copy(selectedCourse = action.course) }
            }
            is SearchClassAction.SelectDayOfWeek -> {
                updateState { it.copy(selectedDayOfWeek = action.dayOfWeek) }
            }
            is SearchClassAction.SelectClassType -> {
                updateState { it.copy(selectedClassType = action.classType) }
            }
            is SearchClassAction.ToggleIsExtraClass -> {
                updateState { it.copy(isExtraClass = action.isExtraClass) }
            }

            SearchClassAction.ResetFilters -> {
                updateState {
                    it.copy(
                        selectedTeacher = null,
                        selectedRoom = null,
                        selectedBatch = null,
                        selectedCourse = null,
                        selectedDayOfWeek = null,
                        selectedClassType = null,
                        isExtraClass = null
                    )
                }
            }
            SearchClassAction.ApplyFilters -> {
                updateState { it.copy(isBottomSheetVisible = false) }
                refreshSearch()
            }

            is SearchClassAction.ClassCardClick -> {
                sendEvent(SearchClassEvent.NavigateToClassDetails(action.classId))
            }

            SearchClassAction.LoadMoreClasses -> loadNextClasses()

            SearchClassAction.ClickFloatingActionButton -> {
                sendEvent(SearchClassEvent.NavigateToAddEditClass)
            }
        }
    }
}
