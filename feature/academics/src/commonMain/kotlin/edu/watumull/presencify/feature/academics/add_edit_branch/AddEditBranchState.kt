package edu.watumull.presencify.feature.academics.add_edit_branch

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.presentation.UiText

data class AddEditBranchState(
    val branchId: String? = null,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,

    val name: String = "",
    val abbreviation: String = "",

    val nameError: String? = null,
    val abbreviationError: String? = null,

    val dialogState: DialogState? = null,
) {
    sealed interface ViewState

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
    CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
}

