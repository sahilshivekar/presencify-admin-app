package edu.watumull.presencify.feature.academics.add_edit_scheme

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.model.academics.University
import edu.watumull.presencify.core.presentation.UiText

data class AddEditSchemeState(
    val schemeId: String? = null,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,

    val name: String = "",
    val selectedUniversityId: String = "",
    val universityOptions: List<University> = emptyList(),

    val nameError: String? = null,
    val universityError: String? = null,

    val isUniversityDropdownOpen: Boolean = false,

    val dialogState: DialogState? = null,
) {
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

