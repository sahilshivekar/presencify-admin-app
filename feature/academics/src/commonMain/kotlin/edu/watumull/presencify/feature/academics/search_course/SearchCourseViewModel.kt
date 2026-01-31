package edu.watumull.presencify.feature.academics.search_course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository
import edu.watumull.presencify.core.presentation.pagination.Paginator
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import edu.watumull.presencify.feature.academics.navigation.SearchCourseIntention
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchCourseViewModel(
    private val courseRepository: CourseRepository,
    private val branchRepository: BranchRepository,
    private val schemeRepository: SchemeRepository,
    private val teacherRepository: TeacherRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SearchCourseState, SearchCourseEvent, SearchCourseAction>(
    initialState = run {
        val routeParams = savedStateHandle.toRoute<AcademicsRoutes.SearchCourse>()
        val intention = try {
            SearchCourseIntention.valueOf(routeParams.intention)
        } catch (_: IllegalArgumentException) {
            SearchCourseIntention.DEFAULT
        }

        // Validate required parameters based on intention
        when (intention) {
            SearchCourseIntention.LINK_UNLINK_COURSE_TO_SEMESTER_NUMBER_BRANCH -> {
                if (routeParams.branchId == null || routeParams.semesterNumber == null) {
                    throw IllegalArgumentException("Branch ID and Semester Number are required for linking/unlinking courses")
                }
            }
            SearchCourseIntention.ASSIGN_UNASSIGN_COURSE_TO_TEACHER -> {
                if (routeParams.teacherId == null) {
                    throw IllegalArgumentException("Teacher ID is required for assigning/unassigning courses")
                }
            }
            SearchCourseIntention.DEFAULT -> {
                // No validation needed
            }
        }

        SearchCourseState(
            intention = intention,
            branchId = routeParams.branchId,
            semesterNumber = routeParams.semesterNumber,
            teacherId = routeParams.teacherId
        )
    }
) {

    private val paginator = Paginator<Int, edu.watumull.presencify.core.domain.model.academics.CourseListWithTotalCount>(
        initialKey = 1,
        onLoadUpdated = { isLoading ->
            updateState { it.copy(isLoadingMore = isLoading) }
        },
        onRequest = { page ->
            val state = stateFlow.value

            courseRepository.getCourses(
                searchQuery = state.searchQuery.ifBlank { null },
                branchId = state.selectedBranch?.id,
                semesterNumber = state.selectedSemesterNumber,
                schemeId = state.selectedScheme?.id,
                teacherIds = state.selectedTeacherIds.takeIf { it.isNotEmpty() }?.toList(),
                page = page,
                limit = 20
            )
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { error ->
            updateState {
                it.copy(
                    dialogState = SearchCourseState.DialogState(
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
                    courses = if (stateFlow.value.currentPage == 1) response.courses.toPersistentList() else it.courses.addAll(
                        response.courses.toPersistentList()
                    ),
                    currentPage = stateFlow.value.currentPage + 1,
                    isRefreshing = false,
                    isLoadingCourses = false
                )
            }
        },
        endReached = { currentPage, response ->
            // End reached when we have loaded all courses
            val totalLoadedCourses = currentPage * 20
            totalLoadedCourses >= response.totalCount
        }
    )

    init {
        // Load initial data
        viewModelScope.launch {
            loadBranches()
            loadSchemes()
            loadTeachers()
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

    private fun loadNextCourses() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun refreshSearch() {
        updateState { it.copy(courses = persistentListOf(), currentPage = 1) }
        paginator.reset()
        loadNextCourses()
    }

    private suspend fun loadBranches() {
        updateState { it.copy(areBranchesLoading = true) }
        branchRepository.getBranches(searchQuery = null)
            .onSuccess { branches ->
                updateState {
                    it.copy(
                        branchOptions = branches.toPersistentList(),
                        areBranchesLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areBranchesLoading = false,
                        dialogState = SearchCourseState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    private suspend fun loadSchemes() {
        updateState { it.copy(areSchemesLoading = true) }
        schemeRepository.getSchemes(searchQuery = null)
            .onSuccess { schemes ->
                updateState {
                    it.copy(
                        schemeOptions = schemes.toPersistentList(),
                        areSchemesLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areSchemesLoading = false,
                        dialogState = SearchCourseState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    private suspend fun loadTeachers() {
        updateState { it.copy(areTeachersLoading = true) }
        teacherRepository.getTeachers(searchQuery = null, getAll = true)
            .onSuccess { response ->
                updateState {
                    it.copy(
                        teacherOptions = response.teachers.toPersistentList(),
                        areTeachersLoading = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        areTeachersLoading = false,
                        dialogState = SearchCourseState.DialogState(
                            dialogType = DialogType.ERROR,
                            title = "Error",
                            message = error.toUiText(),
                            dialogIntention = DialogIntention.GENERIC
                        )
                    )
                }
            }
    }

    private suspend fun handleCourseActionButton(courseId: String) {
        val state = stateFlow.value

        when (state.intention) {
            SearchCourseIntention.ASSIGN_UNASSIGN_COURSE_TO_TEACHER -> {
                handleAssignUnassignCourse(courseId)
            }
            SearchCourseIntention.LINK_UNLINK_COURSE_TO_SEMESTER_NUMBER_BRANCH -> {
                handleLinkUnlinkCourse(courseId)
            }
            SearchCourseIntention.DEFAULT -> {
                // Should not happen
            }
        }
    }

    private suspend fun handleLinkUnlinkCourse(courseId: String) {
        val state = stateFlow.value
        val branchId = state.branchId ?: return
        val semesterNumber = state.semesterNumber?.let {
            edu.watumull.presencify.core.domain.enums.SemesterNumber.fromValue(it)
        } ?: return

        // Prevent duplicate clicks
        if (state.loadingCourseIds.contains(courseId)) return

        // Find the course in the list
        val course = state.courses.find { it.id == courseId } ?: return

        // Check if course is already linked to this branch+semester combination
        val branchCourseSemester = course.branchCourseSemesters?.find {
            it.branchId == branchId && it.semesterNumber == semesterNumber
        }
        val isLinked = branchCourseSemester != null

        // Mark course as loading
        updateState { it.copy(loadingCourseIds = it.loadingCourseIds + courseId) }

        if (isLinked) {
            // Unlink course - need the branchCourseSemesterId
            val branchCourseSemesterId = branchCourseSemester.id

            courseRepository.removeCourseFromBranchWithSemesterNumber(
                branchCourseSemesterId = branchCourseSemesterId
            )
                .onSuccess {
                    // Update the course in the list
                    val updatedCourses = state.courses.map { c ->
                        if (c.id == courseId) {
                            c.copy(
                                branchCourseSemesters = c.branchCourseSemesters?.filter {
                                    !(it.branchId == branchId && it.semesterNumber == semesterNumber)
                                }
                            )
                        } else {
                            c
                        }
                    }.toPersistentList()

                    updateState {
                        it.copy(
                            courses = updatedCourses,
                            loadingCourseIds = it.loadingCourseIds - courseId
                        )
                    }
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            loadingCourseIds = it.loadingCourseIds - courseId,
                            dialogState = SearchCourseState.DialogState(
                                dialogType = DialogType.ERROR,
                                title = "Error Unlinking Course",
                                message = error.toUiText(),
                                dialogIntention = DialogIntention.GENERIC
                            )
                        )
                    }
                }
        } else {
            // Link course
            courseRepository.addCourseToBranchWithSemesterNumber(
                courseId = courseId,
                branchId = branchId,
                semesterNumber = semesterNumber
            )
                .onSuccess {
                    // Fetch the updated course to get the new BranchCourseSemester entry
                    viewModelScope.launch {
                        courseRepository.getCourseById(courseId)
                            .onSuccess { updatedCourse ->
                                // Update the course in the list
                                val updatedCourses = state.courses.map { c ->
                                    if (c.id == courseId) {
                                        updatedCourse
                                    } else {
                                        c
                                    }
                                }.toPersistentList()

                                updateState {
                                    it.copy(
                                        courses = updatedCourses,
                                        loadingCourseIds = it.loadingCourseIds - courseId
                                    )
                                }
                            }
                            .onError { error ->
                                // If we can't fetch the updated course, just remove loading state
                                updateState {
                                    it.copy(
                                        loadingCourseIds = it.loadingCourseIds - courseId,
                                        dialogState = SearchCourseState.DialogState(
                                            dialogType = DialogType.ERROR,
                                            title = "Error Fetching Updated Course",
                                            message = error.toUiText(),
                                            dialogIntention = DialogIntention.GENERIC
                                        )
                                    )
                                }
                            }
                    }
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            loadingCourseIds = it.loadingCourseIds - courseId,
                            dialogState = SearchCourseState.DialogState(
                                dialogType = DialogType.ERROR,
                                title = "Error Linking Course",
                                message = error.toUiText(),
                                dialogIntention = DialogIntention.GENERIC
                            )
                        )
                    }
                }
        }
    }

    private suspend fun handleAssignUnassignCourse(courseId: String) {
        val state = stateFlow.value
        val teacherId = state.teacherId ?: return

        // Prevent duplicate clicks
        if (state.loadingCourseIds.contains(courseId)) return

        // Find the course in the list
        val course = state.courses.find { it.id == courseId } ?: return

        // Check if course is already assigned to this teacher
        val teacherTeachesCourse = course.teacherTeachesCourses?.find { it.teacherId == teacherId }
        val isAssigned = teacherTeachesCourse != null

        // Mark course as loading
        updateState { it.copy(loadingCourseIds = it.loadingCourseIds + courseId) }

        if (isAssigned) {
            // Unassign course - need to find the teacherTeachesCourse.id
            val teacherTeachesCourseId = teacherTeachesCourse.id

            teacherRepository.removeTeachingCourse(teacherTeachesCourseId)
                .onSuccess {
                    // Update the course in the list
                    val updatedCourses = state.courses.map { c ->
                        if (c.id == courseId) {
                            c.copy(
                                teacherTeachesCourses = c.teacherTeachesCourses?.filter { it.teacherId != teacherId }
                            )
                        } else {
                            c
                        }
                    }.toPersistentList()

                    updateState {
                        it.copy(
                            courses = updatedCourses,
                            loadingCourseIds = it.loadingCourseIds - courseId
                        )
                    }
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            loadingCourseIds = it.loadingCourseIds - courseId,
                            dialogState = SearchCourseState.DialogState(
                                dialogType = DialogType.ERROR,
                                title = "Error Unassigning Course",
                                message = error.toUiText(),
                                dialogIntention = DialogIntention.GENERIC
                            )
                        )
                    }
                }
        } else {
            // Assign course
            teacherRepository.addTeachingCourse(teacherId, courseId)
                .onSuccess { teacherTeachesCourse ->
                    // Update the course in the list
                    val updatedCourses = state.courses.map { c ->
                        if (c.id == courseId) {
                            val existingList = c.teacherTeachesCourses ?: emptyList()
                            c.copy(
                                teacherTeachesCourses = existingList + teacherTeachesCourse
                            )
                        } else {
                            c
                        }
                    }.toPersistentList()

                    updateState {
                        it.copy(
                            courses = updatedCourses,
                            loadingCourseIds = it.loadingCourseIds - courseId
                        )
                    }
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            loadingCourseIds = it.loadingCourseIds - courseId,
                            dialogState = SearchCourseState.DialogState(
                                dialogType = DialogType.ERROR,
                                title = "Error Assigning Course",
                                message = error.toUiText(),
                                dialogIntention = DialogIntention.GENERIC
                            )
                        )
                    }
                }
        }
    }

    override fun handleAction(action: SearchCourseAction) {
        when (action) {

            is SearchCourseAction.BackButtonClick -> {
                sendEvent(SearchCourseEvent.NavigateBack)
            }

            is SearchCourseAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }

            is SearchCourseAction.UpdateSearchQuery -> {
                updateState { it.copy(searchQuery = action.query) }
            }

            is SearchCourseAction.Search -> {
                refreshSearch()
            }

            is SearchCourseAction.Refresh -> {
                updateState { it.copy(isRefreshing = true) }
                refreshSearch()
            }

            is SearchCourseAction.SelectSemesterNumber -> {
                updateState { it.copy(selectedSemesterNumber = action.semesterNumber) }
            }

            is SearchCourseAction.SelectBranch -> {
                updateState { it.copy(selectedBranch = action.branch) }
            }

            is SearchCourseAction.SelectScheme -> {
                updateState { it.copy(selectedScheme = action.scheme) }
            }

            is SearchCourseAction.ToggleTeacherSelection -> {
                val currentSelection = stateFlow.value.selectedTeacherIds
                val newSelection = if (currentSelection.contains(action.teacherId)) {
                    currentSelection - action.teacherId
                } else {
                    currentSelection + action.teacherId
                }
                updateState { it.copy(selectedTeacherIds = newSelection) }
            }

            is SearchCourseAction.ResetFilters -> {
                updateState {
                    it.copy(
                        selectedSemesterNumber = null,
                        selectedBranch = null,
                        selectedScheme = null,
                        selectedTeacherIds = emptySet()
                    )
                }
            }

            is SearchCourseAction.ApplyFilters -> {
                refreshSearch()
            }

            is SearchCourseAction.CourseCardClick -> {
                if (stateFlow.value.intention == SearchCourseIntention.DEFAULT) {
                    // Navigate to course details
                    sendEvent(SearchCourseEvent.NavigateToCourseDetails(action.courseId))
                }
                // For other intentions, do nothing (button handles the action)
            }

            is SearchCourseAction.CourseActionButtonClick -> {
                viewModelScope.launch {
                    handleCourseActionButton(action.courseId)
                }
            }

            is SearchCourseAction.LoadMoreCourses -> {
                loadNextCourses()
            }

            SearchCourseAction.HideBottomSheet -> {
                updateState { it.copy(isBottomSheetVisible = false) }
            }

            SearchCourseAction.ShowBottomSheet -> {
                updateState {
                    it.copy(isBottomSheetVisible = true)
                }
            }

            SearchCourseAction.ClickFloatingActionButton -> {
                sendEvent(SearchCourseEvent.NavigateToAddEditCourse)
            }
        }
    }
}
