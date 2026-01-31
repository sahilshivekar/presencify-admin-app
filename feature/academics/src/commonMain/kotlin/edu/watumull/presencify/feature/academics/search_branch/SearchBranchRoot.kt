package edu.watumull.presencify.feature.academics.search_branch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchBranchRoot(
    onNavigateBack: () -> Unit,
    onNavigateToBranchDetails: (String) -> Unit = {},
    onNavigateToAddEditBranch: (String?) -> Unit = {},
) {
    val viewModel: SearchBranchViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SearchBranchEvent.NavigateBack -> onNavigateBack()
            is SearchBranchEvent.NavigateToBranchDetails -> {
                onNavigateToBranchDetails(event.branchId)
            }
            is SearchBranchEvent.NavigateToAddEditBranch -> {
                onNavigateToAddEditBranch(null)
            }
        }
    }

    SearchBranchScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
