package edu.watumull.presencify.feature.academics.scheme_details

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.presentation.UiText

data class SchemeDetailsState(
    val viewState: ViewState = ViewState.Loading,
    val dialogState: DialogState? = null,
    val schemeId: String = "",
    val scheme: Scheme? = null,
    val isRemovingScheme: Boolean = false,
) {
    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: UiText) : ViewState
        data object Content : ViewState
    }

    data class DialogState(
        val isVisible: Boolean = true,
        val dialogType: DialogType = DialogType.INFO,
        val dialogIntention: DialogIntention = DialogIntention.GENERIC,
        val title: String = "",
        val message: UiText? = null,
    )
}

enum class DialogIntention {
    GENERIC,
    CONFIRM_REMOVE_SCHEME,
}

