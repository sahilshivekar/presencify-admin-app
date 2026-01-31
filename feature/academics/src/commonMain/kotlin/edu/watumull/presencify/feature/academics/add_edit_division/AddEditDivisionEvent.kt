package edu.watumull.presencify.feature.academics.add_edit_division

sealed interface AddEditDivisionEvent {
    data object NavigateBack : AddEditDivisionEvent
}

