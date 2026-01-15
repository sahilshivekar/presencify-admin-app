package edu.watumull.presencify.feature.onboarding.select_role

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType

data class SelectRoleState(
    val viewState: ViewState = ViewState.Loading,
    val dialogState: DialogState? = null,
) {
    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: String) : ViewState
        data object Content : ViewState
    }

    data class DialogState(
        val isVisible: Boolean = true,
        val dialogType: DialogType = DialogType.INFO,
        val dialogIntention: DialogIntention = DialogIntention.GENERIC,
        val title: String = "",
        val message: String = "",
    )
}