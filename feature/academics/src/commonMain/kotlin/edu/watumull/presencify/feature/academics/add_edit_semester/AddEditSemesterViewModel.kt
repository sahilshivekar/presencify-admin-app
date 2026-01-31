package edu.watumull.presencify.feature.academics.add_edit_semester

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository
import edu.watumull.presencify.core.domain.repository.academics.SemesterRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class AddEditSemesterViewModel(
    private val semesterRepository: SemesterRepository,
    private val branchRepository: BranchRepository,
    private val schemeRepository: SchemeRepository,
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddEditSemesterState, AddEditSemesterEvent, AddEditSemesterAction>(
    initialState = AddEditSemesterState(
        semesterId = savedStateHandle.toRoute<AcademicsRoutes.AddEditSemester>().semesterId,
        isEditMode = savedStateHandle.toRoute<AcademicsRoutes.AddEditSemester>().semesterId != null
    )
) {

    init {
        viewModelScope.launch {
            loadBranches()
            loadSchemes()
            state.semesterId?.let { id -> loadSemester(id) }
        }
    }

    private suspend fun loadBranches() {
        branchRepository.getBranches()
            .onSuccess { branches ->
                updateState { it.copy(branchOptions = branches) }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        dialogState = AddEditSemesterState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Branches",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private suspend fun loadSchemes() {
        schemeRepository.getSchemes()
            .onSuccess { schemes ->
                updateState { it.copy(schemeOptions = schemes) }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        dialogState = AddEditSemesterState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Schemes",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private suspend fun loadSemester(id: String) {
        updateState { it.copy(isLoading = true) }

        semesterRepository.getSemesterById(id)
            .onSuccess { semester ->
                updateState {
                    it.copy(
                        isLoading = false,
                        semesterNumber = semester.semesterNumber,
                        academicStartYear = semester.academicStartYear.toString(),
                        academicEndYear = semester.academicEndYear.toString(),
                        startDate = semester.startDate,
                        endDate = semester.endDate,
                        selectedBranchId = semester.branchId,
                        selectedSchemeId = semester.schemeId
                    )
                }

                // Fetch optional courses for editing after loading semester details
                fetchOptionalCourses(semester.semesterNumber, semester.branchId, semester.schemeId, semester.courses)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        dialogState = AddEditSemesterState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Semester",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: AddEditSemesterAction) {
        when (action) {
            is AddEditSemesterAction.BackButtonClick -> handleBackNavigation()
            is AddEditSemesterAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is AddEditSemesterAction.UpdateSemesterNumber -> {
                updateState {
                    it.copy(
                        semesterNumber = action.semesterNumber,
                        semesterNumberError = null,
                        isSemesterNumberDropdownOpen = false
                    )
                }
                fetchOptionalCoursesIfReady()
            }

            is AddEditSemesterAction.UpdateAcademicStartYear -> updateState {
                it.copy(
                    academicStartYear = action.year,
                    academicStartYearError = null
                )
            }

            is AddEditSemesterAction.UpdateAcademicEndYear -> updateState {
                it.copy(
                    academicEndYear = action.year,
                    academicEndYearError = null
                )
            }

            is AddEditSemesterAction.UpdateStartDate -> updateState {
                it.copy(
                    startDate = action.date,
                    startDateError = null,
                    isStartDatePickerVisible = false
                )
            }

            is AddEditSemesterAction.UpdateEndDate -> updateState {
                it.copy(
                    endDate = action.date,
                    endDateError = null,
                    isEndDatePickerVisible = false
                )
            }

            is AddEditSemesterAction.UpdateSelectedBranch -> {
                updateState {
                    it.copy(
                        selectedBranchId = action.branchId,
                        branchError = null,
                        isBranchDropdownOpen = false
                    )
                }
                fetchOptionalCoursesIfReady()
            }

            is AddEditSemesterAction.UpdateSelectedScheme -> {
                updateState {
                    it.copy(
                        selectedSchemeId = action.schemeId,
                        schemeError = null,
                        isSchemeDropdownOpen = false
                    )
                }
                fetchOptionalCoursesIfReady()
            }

            is AddEditSemesterAction.ChangeSemesterNumberDropDownVisibility -> updateState {
                it.copy(
                    isSemesterNumberDropdownOpen = action.isVisible
                )
            }

            is AddEditSemesterAction.ChangeBranchDropDownVisibility -> updateState { it.copy(isBranchDropdownOpen = action.isVisible) }
            is AddEditSemesterAction.ChangeSchemeDropDownVisibility -> updateState { it.copy(isSchemeDropdownOpen = action.isVisible) }
            is AddEditSemesterAction.ChangeStartDatePickerVisibility -> updateState { it.copy(isStartDatePickerVisible = action.isVisible) }
            is AddEditSemesterAction.ChangeEndDatePickerVisibility -> updateState { it.copy(isEndDatePickerVisible = action.isVisible) }
            is AddEditSemesterAction.SelectOptionalCourse -> {
                updateState {
                    it.copy(
                        selectedOptionalCourses = it.selectedOptionalCourses + (action.optionalCourse to action.courseId),
                        openOptionalDropdowns = it.openOptionalDropdowns - action.optionalCourse
                    )
                }
            }

            is AddEditSemesterAction.ChangeOptionalCourseDropdownVisibility -> {
                updateState {
                    val newSet = if (action.isVisible) {
                        it.openOptionalDropdowns + action.optionalCourse
                    } else {
                        it.openOptionalDropdowns - action.optionalCourse
                    }
                    it.copy(openOptionalDropdowns = newSet)
                }
            }

            is AddEditSemesterAction.SubmitClick -> {
                viewModelScope.launch { submitForm() }
            }
        }
    }

    private fun handleBackNavigation() {
        if (hasUnsavedChanges()) {
            updateState {
                it.copy(
                    dialogState = AddEditSemesterState.DialogState(
                        dialogType = DialogType.CONFIRM_NORMAL_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
                        title = "Unsaved Changes",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString("You have unsaved changes. Are you sure you want to leave?")
                    )
                )
            }
        } else {
            sendEvent(AddEditSemesterEvent.NavigateBack)
        }
    }

    private fun fetchOptionalCoursesIfReady() {
        val semesterNumber = state.semesterNumber
        val branchId = state.selectedBranchId
        val schemeId = state.selectedSchemeId

        // Fetch if all required filters are selected
        if (semesterNumber != null && branchId.isNotBlank() && schemeId.isNotBlank()) {
            viewModelScope.launch {
                // In edit mode, pass null for existing courses since we're changing filters
                fetchOptionalCourses(semesterNumber, branchId, schemeId, existingCourses = null)
            }
        } else {
            // Clear optional courses if conditions are not met
            updateState {
                it.copy(
                    optionalCourseGroups = emptyMap(),
                    selectedOptionalCourses = emptyMap(),
                    openOptionalDropdowns = emptySet()
                )
            }
        }
    }

    private suspend fun fetchOptionalCourses(
        semesterNumber: SemesterNumber,
        branchId: String,
        schemeId: String,
        existingCourses: List<Course>? = null
    ) {
        updateState { it.copy(isFetchingOptionalCourses = true) }

        courseRepository.getCourses(
            semesterNumber = semesterNumber,
            branchId = branchId,
            schemeId = schemeId,
            onlyOptional = true,
            getAll = true
        )
            .onSuccess { result ->
                // Group courses by optionalCourse
                val groupedCourses = result.courses
                    .filter { it.optionalCourse != null }
                    .groupBy { it.optionalCourse!! }

                // Pre-select existing courses if in edit mode
                val preSelectedCourses = if (existingCourses != null) {
                    // Filter only optional courses from existing courses
                    val existingOptionalCourses = existingCourses.filter { it.optionalCourse != null }

                    // Create map of optionalCourse -> courseId
                    existingOptionalCourses.associate { course ->
                        course.optionalCourse!! to course.id
                    }
                } else {
                    emptyMap()
                }

                updateState {
                    it.copy(
                        isFetchingOptionalCourses = false,
                        optionalCourseGroups = groupedCourses,
                        // Pre-select existing courses or clear selections
                        selectedOptionalCourses = preSelectedCourses,
                        openOptionalDropdowns = emptySet()
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isFetchingOptionalCourses = false,
                        dialogState = AddEditSemesterState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Optional Courses",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    fun confirmNavigateBack() {
        updateState { it.copy(dialogState = null) }
        sendEvent(AddEditSemesterEvent.NavigateBack)
    }

    private fun hasUnsavedChanges(): Boolean {
        return state.semesterNumber != null || state.academicStartYear.isNotBlank() || state.academicEndYear.isNotBlank() || state.startDate != null || state.endDate != null
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val semesterNumberError = if (state.semesterNumber == null) "Semester number is required" else null
        if (semesterNumberError != null) isValid = false

        val startYearError = if (state.academicStartYear.isNotBlank()) {
            val year = state.academicStartYear.toIntOrNull()
            if (year == null || year < 1900 || year > 2100) "Invalid start year" else null
        } else {
            "Academic start year is required"
        }
        if (startYearError != null) isValid = false

        val endYearError = if (state.academicEndYear.isNotBlank()) {
            val year = state.academicEndYear.toIntOrNull()
            if (year == null || year < 1900 || year > 2100) "Invalid end year" else null
        } else {
            "Academic end year is required"
        }
        if (endYearError != null) isValid = false

        val startDateError = if (state.startDate == null) "Start date is required" else null
        if (startDateError != null) isValid = false

        val endDateError = if (state.endDate == null) "End date is required" else null
        if (endDateError != null) isValid = false

        val branchError = if (state.selectedBranchId.isBlank()) "Branch is required" else null
        if (branchError != null) isValid = false

        val schemeError = if (state.selectedSchemeId.isBlank()) "Scheme is required" else null
        if (schemeError != null) isValid = false

        updateState {
            it.copy(
                semesterNumberError = semesterNumberError,
                academicStartYearError = startYearError,
                academicEndYearError = endYearError,
                startDateError = startDateError,
                endDateError = endDateError,
                branchError = branchError,
                schemeError = schemeError
            )
        }

        return isValid
    }

    private suspend fun submitForm() {
        if (!validateForm()) return

        updateState { it.copy(isSubmitting = true) }

        val startYear = state.academicStartYear.toIntOrNull() ?: return
        val endYear = state.academicEndYear.toIntOrNull() ?: return
        val semNum = state.semesterNumber ?: return
        val startDt = state.startDate ?: return
        val endDt = state.endDate ?: return

        // Collect selected optional course IDs (for both add and edit modes)
        val optionalCourseIds = if (state.selectedOptionalCourses.isNotEmpty()) {
            state.selectedOptionalCourses.values.toList()
        } else {
            null
        }

        val result = if (state.isEditMode && state.semesterId != null) {
            val id = state.semesterId!!
            semesterRepository.updateSemester(
                id = id,
                startDate = startDt,
                endDate = endDt,
                optionalCourseIds = optionalCourseIds
            )
        } else {
            semesterRepository.addSemester(
                branchId = state.selectedBranchId,
                semesterNumber = semNum,
                academicStartYear = startYear,
                academicEndYear = endYear,
                startDate = startDt,
                endDate = endDt,
                schemeId = state.selectedSchemeId,
                optionalCourseIds = optionalCourseIds
            )
        }

        result
            .onSuccess {
                updateState { it.copy(isSubmitting = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(SnackbarEvent(message = if (state.isEditMode) "Semester updated successfully" else "Semester added successfully"))
                }
                sendEvent(AddEditSemesterEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isSubmitting = false,
                        dialogState = AddEditSemesterState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = if (state.isEditMode) "Error Updating Semester" else "Error Adding Semester",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

