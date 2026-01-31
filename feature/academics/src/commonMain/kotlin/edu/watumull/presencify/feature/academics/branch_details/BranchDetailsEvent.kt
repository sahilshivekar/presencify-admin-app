package edu.watumull.presencify.feature.academics.branch_details

sealed interface BranchDetailsEvent {
    data object NavigateBack : BranchDetailsEvent
    data class NavigateToEditBranch(val branchId: String) : BranchDetailsEvent
}

