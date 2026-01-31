package edu.watumull.presencify.feature.academics.batch_details

sealed interface BatchDetailsEvent {
    data object NavigateBack : BatchDetailsEvent
    data class NavigateToEditBatch(val batchId: String) : BatchDetailsEvent
}

