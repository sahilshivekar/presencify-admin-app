package edu.watumull.presencify.feature.academics.search_course

import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.model.academics.Scheme

sealed interface SearchCourseAction {
    data object BackButtonClick : SearchCourseAction
    data object DismissDialog : SearchCourseAction

    data object ShowBottomSheet: SearchCourseAction
    data object HideBottomSheet: SearchCourseAction

    // Search & Refresh
    data class UpdateSearchQuery(val query: String) : SearchCourseAction
    data object Search : SearchCourseAction
    data object Refresh : SearchCourseAction

    // Filters
    data class SelectSemesterNumber(val semesterNumber: SemesterNumber?) : SearchCourseAction
    data class SelectBranch(val branch: Branch?) : SearchCourseAction
    data class SelectScheme(val scheme: Scheme?) : SearchCourseAction
    data class ToggleTeacherSelection(val teacherId: String) : SearchCourseAction

    data object ResetFilters : SearchCourseAction
    data object ApplyFilters : SearchCourseAction

    // Course Selection
    data class CourseCardClick(val courseId: String) : SearchCourseAction

    // Course Action Button (Assign/Unassign or Link/Unlink)
    data class CourseActionButtonClick(val courseId: String) : SearchCourseAction

    // Pagination
    data object LoadMoreCourses : SearchCourseAction

    data object ClickFloatingActionButton : SearchCourseAction
}
