package edu.watumull.presencify.feature.academics.add_edit_batch

sealed interface AddEditBatchEvent {
    data object NavigateBack : AddEditBatchEvent
}

