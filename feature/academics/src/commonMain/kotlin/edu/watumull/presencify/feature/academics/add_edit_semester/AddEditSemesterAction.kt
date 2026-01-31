package edu.watumull.presencify.feature.academics.add_edit_semester

import edu.watumull.presencify.core.domain.enums.SemesterNumber
import kotlinx.datetime.LocalDate

sealed interface AddEditSemesterAction {
    data object BackButtonClick : AddEditSemesterAction
    data object DismissDialog : AddEditSemesterAction
    data object SubmitClick : AddEditSemesterAction

    data class UpdateSemesterNumber(val semesterNumber: SemesterNumber) : AddEditSemesterAction
    data class UpdateAcademicStartYear(val year: String) : AddEditSemesterAction
    data class UpdateAcademicEndYear(val year: String) : AddEditSemesterAction
    data class UpdateStartDate(val date: LocalDate) : AddEditSemesterAction
    data class UpdateEndDate(val date: LocalDate) : AddEditSemesterAction
    data class UpdateSelectedBranch(val branchId: String) : AddEditSemesterAction
    data class UpdateSelectedScheme(val schemeId: String) : AddEditSemesterAction

    data class ChangeSemesterNumberDropDownVisibility(val isVisible: Boolean) : AddEditSemesterAction
    data class ChangeBranchDropDownVisibility(val isVisible: Boolean) : AddEditSemesterAction
    data class ChangeSchemeDropDownVisibility(val isVisible: Boolean) : AddEditSemesterAction
    data class ChangeStartDatePickerVisibility(val isVisible: Boolean) : AddEditSemesterAction
    data class ChangeEndDatePickerVisibility(val isVisible: Boolean) : AddEditSemesterAction

    // Optional course actions
    data class SelectOptionalCourse(val optionalCourse: String, val courseId: String) : AddEditSemesterAction
    data class ChangeOptionalCourseDropdownVisibility(val optionalCourse: String, val isVisible: Boolean) : AddEditSemesterAction
}

