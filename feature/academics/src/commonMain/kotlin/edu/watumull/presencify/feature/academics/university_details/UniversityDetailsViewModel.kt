package edu.watumull.presencify.feature.academics.university_details

import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.UniversityRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import kotlinx.coroutines.launch

class UniversityDetailsViewModel(
    private val universityRepository: UniversityRepository,
) : BaseViewModel<UniversityDetailsState, UniversityDetailsEvent, UniversityDetailsAction>(
    initialState = UniversityDetailsState()
) {

    init {
        viewModelScope.launch {
            loadUniversities()
        }
    }

    private suspend fun loadUniversities() {
        updateState { it.copy(viewState = UniversityDetailsState.ViewState.Loading) }

        universityRepository.getUniversities()
            .onSuccess { universities ->
                updateState {
                    it.copy(
                        viewState = UniversityDetailsState.ViewState.Content,
                        universities = universities
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(viewState = UniversityDetailsState.ViewState.Error(error.toUiText()))
                }
            }
    }

    override fun handleAction(action: UniversityDetailsAction) {
        when (action) {
            is UniversityDetailsAction.BackButtonClick -> sendEvent(UniversityDetailsEvent.NavigateBack)
            is UniversityDetailsAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is UniversityDetailsAction.AddUniversityClick -> sendEvent(UniversityDetailsEvent.NavigateToAddUniversity)
            is UniversityDetailsAction.EditUniversityClick -> sendEvent(UniversityDetailsEvent.NavigateToEditUniversity(action.universityId))
            is UniversityDetailsAction.RemoveUniversityClick -> {
                val university = state.universities.find { it.id == action.universityId }
                updateState {
                    it.copy(
                        dialogState = UniversityDetailsState.DialogState(
                            dialogType = DialogType.CONFIRM_RISKY_ACTION,
                            dialogIntention = DialogIntention.CONFIRM_REMOVE_UNIVERSITY,
                            title = "Remove University",
                            message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                                if (university != null) {
                                    "Are you sure you want to remove \"${university.name}\"? This will also remove all associated schemes."
                                } else {
                                    "Are you sure you want to remove this university? This will also remove all associated schemes."
                                }
                            ),
                            universityIdToDelete = action.universityId
                        )
                    )
                }
            }
        }
    }

    fun confirmRemoveUniversity() {
        val universityId = state.dialogState?.universityIdToDelete ?: return
        viewModelScope.launch {
            // Set removingUniversityId only when deletion actually starts
            updateState { it.copy(removingUniversityId = universityId, dialogState = null) }
            removeUniversity(universityId)
        }
    }

    private suspend fun removeUniversity(universityId: String) {

        universityRepository.removeUniversity(universityId)
            .onSuccess {
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "University removed successfully")
                    )
                }
                // Reload universities after successful removal
                loadUniversities()
                updateState { it.copy(removingUniversityId = null) }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        removingUniversityId = null,
                        dialogState = UniversityDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing University",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}
