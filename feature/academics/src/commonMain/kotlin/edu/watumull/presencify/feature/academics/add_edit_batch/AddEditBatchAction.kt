package edu.watumull.presencify.feature.academics.add_edit_batch

import edu.watumull.presencify.core.domain.enums.SemesterNumber

sealed interface AddEditBatchAction {
    data object BackButtonClick : AddEditBatchAction
    data object DismissDialog : AddEditBatchAction
    data object FindDivisionsClick : AddEditBatchAction
    data object SubmitClick : AddEditBatchAction

    data class UpdateSemesterNumber(val semesterNumber: SemesterNumber) : AddEditBatchAction
    data class UpdateAcademicStartYear(val year: String) : AddEditBatchAction
    data class UpdateAcademicEndYear(val year: String) : AddEditBatchAction
    data class UpdateSelectedBranch(val branchId: String) : AddEditBatchAction
    data class UpdateSelectedDivision(val divisionId: String) : AddEditBatchAction
    data class UpdateBatchCode(val code: String) : AddEditBatchAction

    data class ChangeSemesterNumberDropDownVisibility(val isVisible: Boolean) : AddEditBatchAction
    data class ChangeBranchDropDownVisibility(val isVisible: Boolean) : AddEditBatchAction
    data class ChangeDivisionDropDownVisibility(val isVisible: Boolean) : AddEditBatchAction
}

