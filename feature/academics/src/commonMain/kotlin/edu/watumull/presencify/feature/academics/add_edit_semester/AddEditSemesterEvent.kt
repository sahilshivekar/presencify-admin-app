package edu.watumull.presencify.feature.academics.add_edit_semester

sealed interface AddEditSemesterEvent {
    data object NavigateBack : AddEditSemesterEvent
}

