package edu.watumull.presencify.feature.academics.add_edit_course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.core.presentation.validation.validateAsCourseCode
import edu.watumull.presencify.core.presentation.validation.validateAsCourseName
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class AddEditCourseViewModel(
    private val courseRepository: CourseRepository,
    private val schemeRepository: SchemeRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddEditCourseState, AddEditCourseEvent, AddEditCourseAction>(
    initialState = AddEditCourseState(
        courseId = savedStateHandle.toRoute<AcademicsRoutes.AddEditCourse>().courseId,
        isEditMode = savedStateHandle.toRoute<AcademicsRoutes.AddEditCourse>().courseId != null
    )
) {

    init {
        viewModelScope.launch {
            loadSchemes()
            state.courseId?.let { id -> loadCourse(id) }
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
                        dialogState = AddEditCourseState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Schemes",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private suspend fun loadCourse(id: String) {
        updateState { it.copy(isLoading = true) }

        courseRepository.getCourseById(id)
            .onSuccess { course ->
                updateState {
                    it.copy(
                        isLoading = false,
                        code = course.code,
                        name = course.name,
                        optionalCourse = course.optionalCourse ?: "",
                        selectedSchemeId = course.schemeId
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        dialogState = AddEditCourseState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Course",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: AddEditCourseAction) {
        when (action) {
            is AddEditCourseAction.BackButtonClick -> handleBackNavigation()
            is AddEditCourseAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is AddEditCourseAction.UpdateCode -> updateState { it.copy(code = action.code, codeError = null) }
            is AddEditCourseAction.UpdateName -> updateState { it.copy(name = action.name, nameError = null) }
            is AddEditCourseAction.UpdateOptionalCourse -> updateState { it.copy(optionalCourse = action.optionalCourse) }
            is AddEditCourseAction.UpdateSelectedScheme -> updateState { it.copy(selectedSchemeId = action.schemeId, schemeError = null, isSchemeDropdownOpen = false) }
            is AddEditCourseAction.ChangeSchemeDropDownVisibility -> updateState { it.copy(isSchemeDropdownOpen = action.isVisible) }
            is AddEditCourseAction.SubmitClick -> { viewModelScope.launch { submitForm() } }
        }
    }

    private fun handleBackNavigation() {
        if (hasUnsavedChanges()) {
            updateState {
                it.copy(
                    dialogState = AddEditCourseState.DialogState(
                        dialogType = DialogType.CONFIRM_NORMAL_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
                        title = "Unsaved Changes",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString("You have unsaved changes. Are you sure you want to leave?")
                    )
                )
            }
        } else {
            sendEvent(AddEditCourseEvent.NavigateBack)
        }
    }

    fun confirmNavigateBack() {
        updateState { it.copy(dialogState = null) }
        sendEvent(AddEditCourseEvent.NavigateBack)
    }

    private fun hasUnsavedChanges(): Boolean {
        return state.code.isNotBlank() || state.name.isNotBlank()
    }

    private fun validateForm(): Boolean {
        val codeValidation = state.code.validateAsCourseCode()
        val codeError = if (codeValidation.successful) null else codeValidation.errorMessage

        val nameValidation = state.name.validateAsCourseName()
        val nameError = if (nameValidation.successful) null else nameValidation.errorMessage

        val schemeValidation = if (state.selectedSchemeId.isNotBlank()) null else "Scheme is required"

        updateState { it.copy(codeError = codeError, nameError = nameError, schemeError = schemeValidation) }

        return codeError == null && nameError == null && schemeValidation == null
    }

    private suspend fun submitForm() {
        if (!validateForm()) return

        updateState { it.copy(isSubmitting = true) }

        val result = if (state.isEditMode && state.courseId != null) {
            val id = state.courseId!!
            courseRepository.updateCourse(id, code = state.code, name = state.name, optionalCourse = state.optionalCourse, schemeId = state.selectedSchemeId)
        } else {
            courseRepository.addCourse(code = state.code, name = state.name, optionalCourse = if (state.optionalCourse.isBlank()) null else state.optionalCourse, schemeId = state.selectedSchemeId)
        }

        result
            .onSuccess {
                updateState { it.copy(isSubmitting = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(SnackbarEvent(message = if (state.isEditMode) "Course updated successfully" else "Course added successfully"))
                }
                sendEvent(AddEditCourseEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isSubmitting = false,
                        dialogState = AddEditCourseState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = if (state.isEditMode) "Error Updating Course" else "Error Adding Course",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

