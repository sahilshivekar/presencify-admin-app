package edu.watumull.presencify.feature.academics.add_edit_division

import edu.watumull.presencify.core.domain.enums.SemesterNumber

sealed interface AddEditDivisionAction {
    data object BackButtonClick : AddEditDivisionAction
    data object DismissDialog : AddEditDivisionAction
    data object FindSemesterClick : AddEditDivisionAction
    data object SubmitClick : AddEditDivisionAction

    data class UpdateSemesterNumber(val semesterNumber: SemesterNumber) : AddEditDivisionAction
    data class UpdateAcademicStartYear(val year: String) : AddEditDivisionAction
    data class UpdateAcademicEndYear(val year: String) : AddEditDivisionAction
    data class UpdateSelectedBranch(val branchId: String) : AddEditDivisionAction
    data class UpdateDivisionCode(val code: String) : AddEditDivisionAction

    data class ChangeSemesterNumberDropDownVisibility(val isVisible: Boolean) : AddEditDivisionAction
    data class ChangeBranchDropDownVisibility(val isVisible: Boolean) : AddEditDivisionAction
}

