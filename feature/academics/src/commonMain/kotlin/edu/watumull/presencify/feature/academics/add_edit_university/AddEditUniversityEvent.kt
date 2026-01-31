package edu.watumull.presencify.feature.academics.add_edit_university

sealed interface AddEditUniversityEvent {
    data object NavigateBack : AddEditUniversityEvent
}
