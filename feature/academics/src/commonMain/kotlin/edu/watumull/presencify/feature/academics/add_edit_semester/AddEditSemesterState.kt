package edu.watumull.presencify.feature.academics.add_edit_semester

import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.presentation.UiText
import kotlinx.datetime.LocalDate

data class AddEditSemesterState(
    val semesterId: String? = null,
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isFetchingOptionalCourses: Boolean = false,

    val semesterNumber: SemesterNumber? = null,
    val academicStartYear: String = "",
    val academicEndYear: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val selectedBranchId: String = "",
    val selectedSchemeId: String = "",

    val branchOptions: List<Branch> = emptyList(),
    val schemeOptions: List<Scheme> = emptyList(),

    // Optional courses grouped by optionalCourse
    val optionalCourseGroups: Map<String, List<Course>> = emptyMap(),
    // Selected course for each optional course group
    val selectedOptionalCourses: Map<String, String> = emptyMap(),
    // Track which dropdown is open for each optional course
    val openOptionalDropdowns: Set<String> = emptySet(),

    val semesterNumberError: String? = null,
    val academicStartYearError: String? = null,
    val academicEndYearError: String? = null,
    val startDateError: String? = null,
    val endDateError: String? = null,
    val branchError: String? = null,
    val schemeError: String? = null,

    val isSemesterNumberDropdownOpen: Boolean = false,
    val isBranchDropdownOpen: Boolean = false,
    val isSchemeDropdownOpen: Boolean = false,
    val isStartDatePickerVisible: Boolean = false,
    val isEndDatePickerVisible: Boolean = false,

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

