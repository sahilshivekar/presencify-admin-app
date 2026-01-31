package edu.watumull.presencify.feature.academics.add_edit_course

sealed interface AddEditCourseEvent {
    data object NavigateBack : AddEditCourseEvent
}

