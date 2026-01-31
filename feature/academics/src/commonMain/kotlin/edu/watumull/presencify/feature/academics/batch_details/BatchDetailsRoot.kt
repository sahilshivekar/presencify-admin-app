package edu.watumull.presencify.feature.academics.batch_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BatchDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditBatch: (String) -> Unit = {},
) {
    val viewModel: BatchDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is BatchDetailsEvent.NavigateBack -> onNavigateBack()
            is BatchDetailsEvent.NavigateToEditBatch -> onNavigateToEditBatch(event.batchId)
        }
    }

    BatchDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction,
        onConfirmRemove = viewModel::confirmRemoveBatch
    )
}
