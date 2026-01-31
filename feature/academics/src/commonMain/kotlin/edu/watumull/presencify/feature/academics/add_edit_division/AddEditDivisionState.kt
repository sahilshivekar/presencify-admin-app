package edu.watumull.presencify.feature.academics.add_edit_division

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.model.academics.Semester
import edu.watumull.presencify.core.presentation.UiText

data class AddEditDivisionState(
    val divisionId: String? = null,
    val isEditMode: Boolean = false,
    val isLoadingDivision: Boolean = false,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,

    // Semester selection fields
    val semesterNumber: SemesterNumber? = null,
    val academicStartYear: String = "",
    val academicEndYear: String = "",
    val selectedBranchId: String = "",

    val branchOptions: List<Branch> = emptyList(),

    // Division fields (shown after semester is found)
    val foundSemester: Semester? = null,
    val divisionCode: String = "",
    val showDivisionInput: Boolean = false,

    // Error states
    val semesterNumberError: String? = null,
    val academicStartYearError: String? = null,
    val academicEndYearError: String? = null,
    val branchError: String? = null,
    val divisionCodeError: String? = null,

    val isSemesterNumberDropdownOpen: Boolean = false,
    val isBranchDropdownOpen: Boolean = false,

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

