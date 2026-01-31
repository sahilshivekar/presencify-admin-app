package edu.watumull.presencify.feature.academics.university_details

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.model.academics.University
import edu.watumull.presencify.core.presentation.UiText

data class UniversityDetailsState(
    val viewState: ViewState = ViewState.Loading,
    val dialogState: DialogState? = null,
    val universities: List<University> = emptyList(),
    val removingUniversityId: String? = null,
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
        val universityIdToDelete: String? = null,
    )
}

enum class DialogIntention {
    GENERIC,
    CONFIRM_REMOVE_UNIVERSITY,
}
