package edu.watumull.presencify.feature.academics.link_unlink_course

import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch

sealed interface LinkUnlinkCourseAction {
    data object BackButtonClick : LinkUnlinkCourseAction
    data object DismissDialog : LinkUnlinkCourseAction

    data class SelectBranch(val branch: Branch?) : LinkUnlinkCourseAction
    data class SelectSemesterNumber(val semesterNumber: SemesterNumber?) : LinkUnlinkCourseAction

    data class ChangeBranchDropDownVisibility(val isOpen: Boolean) : LinkUnlinkCourseAction
    data class ChangeSemesterDropDownVisibility(val isOpen: Boolean) : LinkUnlinkCourseAction

    data object LinkCoursesClick : LinkUnlinkCourseAction
}
