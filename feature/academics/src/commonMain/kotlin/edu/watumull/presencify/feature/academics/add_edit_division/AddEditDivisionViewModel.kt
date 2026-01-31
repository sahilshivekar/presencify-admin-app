package edu.watumull.presencify.feature.academics.add_edit_division

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository
import edu.watumull.presencify.core.domain.repository.academics.DivisionRepository
import edu.watumull.presencify.core.domain.repository.academics.SemesterRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class AddEditDivisionViewModel(
    private val divisionRepository: DivisionRepository,
    private val semesterRepository: SemesterRepository,
    private val branchRepository: BranchRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddEditDivisionState, AddEditDivisionEvent, AddEditDivisionAction>(
    initialState = AddEditDivisionState(
        divisionId = savedStateHandle.toRoute<AcademicsRoutes.AddEditDivision>().divisionId,
        isEditMode = savedStateHandle.toRoute<AcademicsRoutes.AddEditDivision>().divisionId != null
    )
) {

    init {
        viewModelScope.launch {
            val divisionId = state.divisionId
            if (state.isEditMode && divisionId != null) {
                loadDivisionForEdit(divisionId)
            } else {
                loadBranches()
            }
        }
    }

    private suspend fun loadDivisionForEdit(divisionId: String) {
        updateState { it.copy(isLoadingDivision = true) }

        divisionRepository.getDivisionById(divisionId)
            .onSuccess { division ->
                // Fetch the semester to get full details
                semesterRepository.getSemesterById(division.semesterId)
                    .onSuccess { semester ->
                        updateState {
                            it.copy(
                                isLoadingDivision = false,
                                divisionCode = division.divisionCode,
                                foundSemester = semester,
                                showDivisionInput = true
                            )
                        }
                    }
                    .onError { error ->
                        updateState {
                            it.copy(
                                isLoadingDivision = false,
                                dialogState = AddEditDivisionState.DialogState(
                                    dialogType = DialogType.ERROR,
                                    dialogIntention = DialogIntention.GENERIC,
                                    title = "Error Loading Semester",
                                    message = error.toUiText()
                                )
                            )
                        }
                    }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoadingDivision = false,
                        dialogState = AddEditDivisionState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Division",
                            message = error.toUiText()
                        )
                    )
                }
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
                        dialogState = AddEditDivisionState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Branches",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: AddEditDivisionAction) {
        when (action) {
            is AddEditDivisionAction.BackButtonClick -> handleBackNavigation()
            is AddEditDivisionAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is AddEditDivisionAction.UpdateSemesterNumber -> updateState { it.copy(semesterNumber = action.semesterNumber, semesterNumberError = null, isSemesterNumberDropdownOpen = false) }
            is AddEditDivisionAction.UpdateAcademicStartYear -> updateState { it.copy(academicStartYear = action.year, academicStartYearError = null) }
            is AddEditDivisionAction.UpdateAcademicEndYear -> updateState { it.copy(academicEndYear = action.year, academicEndYearError = null) }
            is AddEditDivisionAction.UpdateSelectedBranch -> updateState { it.copy(selectedBranchId = action.branchId, branchError = null, isBranchDropdownOpen = false) }
            is AddEditDivisionAction.UpdateDivisionCode -> updateState { it.copy(divisionCode = action.code, divisionCodeError = null) }
            is AddEditDivisionAction.ChangeSemesterNumberDropDownVisibility -> updateState { it.copy(isSemesterNumberDropdownOpen = action.isVisible) }
            is AddEditDivisionAction.ChangeBranchDropDownVisibility -> updateState { it.copy(isBranchDropdownOpen = action.isVisible) }
            is AddEditDivisionAction.FindSemesterClick -> { viewModelScope.launch { findSemester() } }
            is AddEditDivisionAction.SubmitClick -> { viewModelScope.launch { submitForm() } }
        }
    }

    private fun handleBackNavigation() {
        if (hasUnsavedChanges()) {
            updateState {
                it.copy(
                    dialogState = AddEditDivisionState.DialogState(
                        dialogType = DialogType.CONFIRM_NORMAL_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
                        title = "Unsaved Changes",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString("You have unsaved changes. Are you sure you want to leave?")
                    )
                )
            }
        } else {
            sendEvent(AddEditDivisionEvent.NavigateBack)
        }
    }

    fun confirmNavigateBack() {
        updateState { it.copy(dialogState = null) }
        sendEvent(AddEditDivisionEvent.NavigateBack)
    }

    private fun hasUnsavedChanges(): Boolean {
        return state.divisionCode.isNotBlank() || state.foundSemester != null
    }

    private suspend fun findSemester() {
        if (!validateSemesterInputs()) return

        updateState { it.copy(isLoading = true) }

        val startYear = state.academicStartYear.toIntOrNull() ?: return
        val endYear = state.academicEndYear.toIntOrNull() ?: return
        val semNum = state.semesterNumber ?: return

        semesterRepository.getSemesters(
            semesterNumber = semNum,
            academicStartYear = startYear,
            academicEndYear = endYear,
            branchId = state.selectedBranchId,
            getAll = true
        )
            .onSuccess { result ->
                val semesters = result.semesters
                if (semesters.isNotEmpty()) {
                    // Use the first matching semester
                    updateState { it.copy(isLoading = false, foundSemester = semesters[0], showDivisionInput = true, divisionCodeError = null) }
                } else {
                    updateState {
                        it.copy(
                            isLoading = false,
                            dialogState = AddEditDivisionState.DialogState(
                                dialogType = DialogType.INFO,
                                dialogIntention = DialogIntention.GENERIC,
                                title = "No Semester Found",
                                message = edu.watumull.presencify.core.presentation.UiText.DynamicString("No semester found with the selected criteria. Please check your inputs.")
                            )
                        )
                    }
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        dialogState = AddEditDivisionState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Finding Semester",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private fun validateSemesterInputs(): Boolean {
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

        val branchError = if (state.selectedBranchId.isBlank()) "Branch is required" else null
        if (branchError != null) isValid = false

        updateState {
            it.copy(
                semesterNumberError = semesterNumberError,
                academicStartYearError = startYearError,
                academicEndYearError = endYearError,
                branchError = branchError
            )
        }

        return isValid
    }

    private fun validateDivisionCode(): Boolean {
        val codeError = if (state.divisionCode.isNotBlank()) null else "Division code is required"
        updateState { it.copy(divisionCodeError = codeError) }
        return codeError == null
    }

    private suspend fun submitForm() {
        if (!validateDivisionCode() || state.foundSemester == null) return

        updateState { it.copy(isSubmitting = true) }

        val divisionId = state.divisionId
        val result = if (state.isEditMode && divisionId != null) {
            divisionRepository.updateDivision(
                id = divisionId,
                divisionCode = state.divisionCode
            )
        } else {
            divisionRepository.addDivision(
                divisionCode = state.divisionCode,
                semesterId = state.foundSemester!!.id
            )
        }

        result
            .onSuccess {
                updateState { it.copy(isSubmitting = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = if (state.isEditMode) "Division updated successfully" else "Division added successfully"
                        )
                    )
                }
                sendEvent(AddEditDivisionEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isSubmitting = false,
                        dialogState = AddEditDivisionState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = if (state.isEditMode) "Error Updating Division" else "Error Adding Division",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

