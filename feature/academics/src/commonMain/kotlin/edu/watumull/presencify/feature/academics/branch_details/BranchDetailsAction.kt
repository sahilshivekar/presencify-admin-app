package edu.watumull.presencify.feature.academics.branch_details

sealed interface BranchDetailsAction {
    data object BackButtonClick : BranchDetailsAction
    data object DismissDialog : BranchDetailsAction
    data object RemoveBranchClick : BranchDetailsAction
    data object EditBranchClick : BranchDetailsAction
}

