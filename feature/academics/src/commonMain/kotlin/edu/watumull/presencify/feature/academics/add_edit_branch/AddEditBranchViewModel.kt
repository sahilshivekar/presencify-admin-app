package edu.watumull.presencify.feature.academics.add_edit_branch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.core.presentation.validation.validateAsBranchAbbreviation
import edu.watumull.presencify.core.presentation.validation.validateAsBranchName
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class AddEditBranchViewModel(
    private val branchRepository: BranchRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AddEditBranchState, AddEditBranchEvent, AddEditBranchAction>(
    initialState = AddEditBranchState(
        branchId = savedStateHandle.toRoute<AcademicsRoutes.AddEditBranch>().branchId,
        isEditMode = savedStateHandle.toRoute<AcademicsRoutes.AddEditBranch>().branchId != null
    )
) {

    init {
        viewModelScope.launch {
            state.branchId?.let { id -> loadBranch(id) }
        }
    }

    private suspend fun loadBranch(id: String) {
        updateState { it.copy(isLoading = true) }

        branchRepository.getBranchById(id)
            .onSuccess { branch ->
                updateState {
                    it.copy(
                        isLoading = false,
                        name = branch.name,
                        abbreviation = branch.abbreviation
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        dialogState = AddEditBranchState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Branch",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: AddEditBranchAction) {
        when (action) {
            is AddEditBranchAction.BackButtonClick -> handleBackNavigation()
            is AddEditBranchAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is AddEditBranchAction.UpdateName -> updateState { it.copy(name = action.name, nameError = null) }
            is AddEditBranchAction.UpdateAbbreviation -> updateState { it.copy(abbreviation = action.abbreviation, abbreviationError = null) }
            is AddEditBranchAction.SubmitClick -> {
                viewModelScope.launch {
                    submitForm()
                }
            }
        }
    }

    private fun handleBackNavigation() {
        if (hasUnsavedChanges()) {
            updateState {
                it.copy(
                    dialogState = AddEditBranchState.DialogState(
                        dialogType = DialogType.CONFIRM_NORMAL_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
                        title = "Unsaved Changes",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString("You have unsaved changes. Are you sure you want to leave?")
                    )
                )
            }
        } else {
            sendEvent(AddEditBranchEvent.NavigateBack)
        }
    }

    fun confirmNavigateBack() {
        updateState { it.copy(dialogState = null) }
        sendEvent(AddEditBranchEvent.NavigateBack)
    }

    private fun hasUnsavedChanges(): Boolean {
        return state.name.isNotBlank() || state.abbreviation.isNotBlank()
    }

    private fun validateForm(): Boolean {
        val nameValidation = state.name.validateAsBranchName()
        val nameError = if (nameValidation.successful) null else nameValidation.errorMessage

        val abbrValidation = state.abbreviation.validateAsBranchAbbreviation()
        val abbrError = if (abbrValidation.successful) null else abbrValidation.errorMessage

        updateState {
            it.copy(
                nameError = nameError,
                abbreviationError = abbrError
            )
        }

        return nameError == null && abbrError == null
    }

    private suspend fun submitForm() {
        if (!validateForm()) return

        updateState { it.copy(isSubmitting = true) }

        val result = if (state.isEditMode && state.branchId != null) {
            val id = state.branchId!!
            branchRepository.updateBranch(id, name = state.name, abbreviation = state.abbreviation)
        } else {
            branchRepository.addBranch(name = state.name, abbreviation = state.abbreviation)
        }

        result
            .onSuccess {
                updateState { it.copy(isSubmitting = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = if (state.isEditMode) "Branch updated successfully" else "Branch added successfully")
                    )
                }
                sendEvent(AddEditBranchEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isSubmitting = false,
                        dialogState = AddEditBranchState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = if (state.isEditMode) "Error Updating Branch" else "Error Adding Branch",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}
