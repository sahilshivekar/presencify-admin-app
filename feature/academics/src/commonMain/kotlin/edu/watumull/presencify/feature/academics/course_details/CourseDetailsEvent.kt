package edu.watumull.presencify.feature.academics.course_details

sealed interface CourseDetailsEvent {
    data object NavigateBack : CourseDetailsEvent
    data class NavigateToEditCourse(val courseId: String) : CourseDetailsEvent
}

