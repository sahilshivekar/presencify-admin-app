package edu.watumull.presencify.feature.academics.course_details

sealed interface CourseDetailsAction {
    data object BackButtonClick : CourseDetailsAction
    data object DismissDialog : CourseDetailsAction
    data object RemoveCourseClick : CourseDetailsAction
    data object ConfirmRemoveCourse : CourseDetailsAction
    data object EditCourseClick : CourseDetailsAction
}

