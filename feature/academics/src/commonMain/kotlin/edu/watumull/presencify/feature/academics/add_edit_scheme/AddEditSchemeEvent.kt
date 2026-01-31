package edu.watumull.presencify.feature.academics.add_edit_scheme

sealed interface AddEditSchemeEvent {
    data object NavigateBack : AddEditSchemeEvent
}

