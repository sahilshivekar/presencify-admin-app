package edu.watumull.presencify.feature.academics.branch_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BranchDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditBranch: (String) -> Unit = {},
) {
    val viewModel: BranchDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is BranchDetailsEvent.NavigateBack -> onNavigateBack()
            is BranchDetailsEvent.NavigateToEditBranch -> onNavigateToEditBranch(event.branchId)
        }
    }

    BranchDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction,
        onConfirmRemove = viewModel::confirmRemoveBranch
    )
}

