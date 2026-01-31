package edu.watumull.presencify.feature.academics.scheme_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class SchemeDetailsViewModel(
    private val schemeRepository: SchemeRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SchemeDetailsState, SchemeDetailsEvent, SchemeDetailsAction>(
    initialState = SchemeDetailsState(
        schemeId = savedStateHandle.toRoute<AcademicsRoutes.SchemeDetails>().schemeId
    )
) {

    init {
        viewModelScope.launch {
            loadScheme()
        }
    }

    private suspend fun loadScheme() {
        val schemeId = state.schemeId

        schemeRepository.getSchemeById(schemeId)
            .onSuccess { scheme ->
                updateState { it.copy(viewState = SchemeDetailsState.ViewState.Content, scheme = scheme) }
            }
            .onError { error ->
                updateState { it.copy(viewState = SchemeDetailsState.ViewState.Error(error.toUiText())) }
            }
    }

    override fun handleAction(action: SchemeDetailsAction) {
        when (action) {
            is SchemeDetailsAction.BackButtonClick -> sendEvent(SchemeDetailsEvent.NavigateBack)
            is SchemeDetailsAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is SchemeDetailsAction.RemoveSchemeClick -> updateState {
                it.copy(
                    dialogState = SchemeDetailsState.DialogState(
                        dialogType = DialogType.CONFIRM_RISKY_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_REMOVE_SCHEME,
                        title = "Remove Scheme",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                            "Are you sure you want to remove ${state.scheme?.name ?: "this scheme"}?"
                        )
                    )
                )
            }
            is SchemeDetailsAction.ConfirmRemoveScheme -> {
                viewModelScope.launch {
                    removeScheme()
                }
            }
            is SchemeDetailsAction.EditSchemeClick -> sendEvent(SchemeDetailsEvent.NavigateToEditScheme(state.schemeId))
        }
    }

    private suspend fun removeScheme() {
        val schemeId = state.scheme?.id ?: return

        updateState { it.copy(isRemovingScheme = true, dialogState = null) }

        schemeRepository.removeScheme(schemeId)
            .onSuccess {
                updateState { it.copy(isRemovingScheme = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Scheme removed successfully")
                    )
                }
                sendEvent(SchemeDetailsEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isRemovingScheme = false,
                        dialogState = SchemeDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing Scheme",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

