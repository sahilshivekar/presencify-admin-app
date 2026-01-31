package edu.watumull.presencify.feature.academics.division_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.DivisionRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class DivisionDetailsViewModel(
    private val divisionRepository: DivisionRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<DivisionDetailsState, DivisionDetailsEvent, DivisionDetailsAction>(
    initialState = DivisionDetailsState(
        divisionId = savedStateHandle.toRoute<AcademicsRoutes.DivisionDetails>().divisionId
    )
) {

    init {
        viewModelScope.launch {
            loadDivision()
        }
    }

    private suspend fun loadDivision() {
        val divisionId = state.divisionId

        divisionRepository.getDivisionById(divisionId)
            .onSuccess { division ->
                updateState { it.copy(viewState = DivisionDetailsState.ViewState.Content, division = division) }
            }
            .onError { error ->
                updateState { it.copy(viewState = DivisionDetailsState.ViewState.Error(error.toUiText())) }
            }
    }

    override fun handleAction(action: DivisionDetailsAction) {
        when (action) {
            is DivisionDetailsAction.BackButtonClick -> sendEvent(DivisionDetailsEvent.NavigateBack)
            is DivisionDetailsAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is DivisionDetailsAction.RemoveDivisionClick -> updateState {
                it.copy(
                    dialogState = DivisionDetailsState.DialogState(
                        dialogType = DialogType.CONFIRM_RISKY_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_REMOVE_DIVISION,
                        title = "Remove Division",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                            "Are you sure you want to remove this semester? This will also remove all associated batches"
                        )
                    )
                )
            }
            is DivisionDetailsAction.EditDivisionClick -> sendEvent(DivisionDetailsEvent.NavigateToEditDivision(state.divisionId))
        }
    }

    fun confirmRemoveDivision() {
        viewModelScope.launch {
            removeDivision()
        }
    }

    private suspend fun removeDivision() {
        val divisionId = state.division?.id ?: return

        updateState { it.copy(isRemovingDivision = true, dialogState = null) }

        divisionRepository.removeDivision(divisionId)
            .onSuccess {
                updateState { it.copy(isRemovingDivision = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Division removed successfully")
                    )
                }
                sendEvent(DivisionDetailsEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isRemovingDivision = false,
                        dialogState = DivisionDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing Division",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

