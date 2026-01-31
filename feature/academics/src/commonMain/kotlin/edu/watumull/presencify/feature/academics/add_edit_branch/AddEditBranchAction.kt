package edu.watumull.presencify.feature.academics.add_edit_branch

sealed interface AddEditBranchAction {
    data object BackButtonClick : AddEditBranchAction
    data object DismissDialog : AddEditBranchAction
    data object SubmitClick : AddEditBranchAction

    data class UpdateName(val name: String) : AddEditBranchAction
    data class UpdateAbbreviation(val abbreviation: String) : AddEditBranchAction
}

