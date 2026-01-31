package edu.watumull.presencify.feature.academics.search_batch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchBatchRoot(
    onNavigateBack: () -> Unit,
    onNavigateToBatchDetails: (String) -> Unit = {},
    onNavigateToAddEditBatch: (String?) -> Unit = {},
) {
    val viewModel: SearchBatchViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SearchBatchEvent.NavigateBack -> onNavigateBack()
            is SearchBatchEvent.NavigateToBatchDetails -> {
                onNavigateToBatchDetails(event.batchId)
            }
            is SearchBatchEvent.NavigateToAddEditBatch -> {
                onNavigateToAddEditBatch(null)
            }
        }
    }

    SearchBatchScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
