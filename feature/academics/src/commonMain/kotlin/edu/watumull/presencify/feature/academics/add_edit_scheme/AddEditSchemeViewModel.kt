package edu.watumull.presencify.feature.academics.add_edit_scheme

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository
import edu.watumull.presencify.core.domain.repository.academics.UniversityRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.core.presentation.validation.validateAsSchemeName
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class AddEditSchemeViewModel(
    private val schemeRepository: SchemeRepository,
    private val universityRepository: UniversityRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddEditSchemeState, AddEditSchemeEvent, AddEditSchemeAction>(
    initialState = AddEditSchemeState(
        schemeId = savedStateHandle.toRoute<AcademicsRoutes.AddEditScheme>().schemeId,
        isEditMode = savedStateHandle.toRoute<AcademicsRoutes.AddEditScheme>().schemeId != null
    )
) {

    init {
        viewModelScope.launch {
            loadUniversities()
            state.schemeId?.let { id -> loadScheme(id) }
        }
    }

    private suspend fun loadUniversities() {
        universityRepository.getUniversities()
            .onSuccess { universities ->
                updateState { it.copy(universityOptions = universities) }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        dialogState = AddEditSchemeState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Universities",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private suspend fun loadScheme(id: String) {
        updateState { it.copy(isLoading = true) }

        schemeRepository.getSchemeById(id)
            .onSuccess { scheme ->
                updateState {
                    it.copy(
                        isLoading = false,
                        name = scheme.name,
                        selectedUniversityId = scheme.universityId
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        dialogState = AddEditSchemeState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Scheme",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: AddEditSchemeAction) {
        when (action) {
            is AddEditSchemeAction.BackButtonClick -> handleBackNavigation()
            is AddEditSchemeAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is AddEditSchemeAction.UpdateName -> updateState { it.copy(name = action.name, nameError = null) }
            is AddEditSchemeAction.UpdateSelectedUniversity -> updateState { it.copy(selectedUniversityId = action.universityId, universityError = null, isUniversityDropdownOpen = false) }
            is AddEditSchemeAction.ChangeUniversityDropDownVisibility -> updateState { it.copy(isUniversityDropdownOpen = action.isVisible) }
            is AddEditSchemeAction.SubmitClick -> {
                viewModelScope.launch { submitForm() }
            }
        }
    }

    private fun handleBackNavigation() {
        if (hasUnsavedChanges()) {
            updateState {
                it.copy(
                    dialogState = AddEditSchemeState.DialogState(
                        dialogType = DialogType.CONFIRM_NORMAL_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
                        title = "Unsaved Changes",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString("You have unsaved changes. Are you sure you want to leave?")
                    )
                )
            }
        } else {
            sendEvent(AddEditSchemeEvent.NavigateBack)
        }
    }

    fun confirmNavigateBack() {
        updateState { it.copy(dialogState = null) }
        sendEvent(AddEditSchemeEvent.NavigateBack)
    }

    private fun hasUnsavedChanges(): Boolean {
        return state.name.isNotBlank() || state.selectedUniversityId.isNotBlank()
    }

    private fun validateForm(): Boolean {
        val nameValidation = state.name.validateAsSchemeName()
        val nameError = if (nameValidation.successful) null else nameValidation.errorMessage

        val uniValidation = if (state.selectedUniversityId.isNotBlank()) null else "University is required"

        updateState { it.copy(nameError = nameError, universityError = uniValidation) }

        return nameError == null && uniValidation == null
    }

    private suspend fun submitForm() {
        if (!validateForm()) return

        updateState { it.copy(isSubmitting = true) }

        val result = if (state.isEditMode && state.schemeId != null) {
            val id = state.schemeId!!
            schemeRepository.updateScheme(id, name = state.name)
        } else {
            schemeRepository.addScheme(name = state.name, universityId = state.selectedUniversityId)
        }

        result
            .onSuccess {
                updateState { it.copy(isSubmitting = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = if (state.isEditMode) "Scheme updated successfully" else "Scheme added successfully")
                    )
                }
                sendEvent(AddEditSchemeEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isSubmitting = false,
                        dialogState = AddEditSchemeState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = if (state.isEditMode) "Error Updating Scheme" else "Error Adding Scheme",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}
