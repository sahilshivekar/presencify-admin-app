package edu.watumull.presencify.feature.academics.add_edit_branch

sealed interface AddEditBranchEvent {
    data object NavigateBack : AddEditBranchEvent
}

