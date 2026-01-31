package edu.watumull.presencify.feature.academics.link_unlink_course

sealed interface LinkUnlinkCourseEvent {
    data object NavigateBack : LinkUnlinkCourseEvent
    data class NavigateToSearchCourse(
        val branchId: String,
        val semesterNumber: Int
    ) : LinkUnlinkCourseEvent
}
