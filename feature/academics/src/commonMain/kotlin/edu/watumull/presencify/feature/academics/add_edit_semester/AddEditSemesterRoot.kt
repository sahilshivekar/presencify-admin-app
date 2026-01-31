package edu.watumull.presencify.feature.academics.add_edit_semester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditSemesterRoot(
    onNavigateBack: () -> Unit,
) {
    val viewModel: AddEditSemesterViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is AddEditSemesterEvent.NavigateBack -> onNavigateBack()
        }
    }

    AddEditSemesterScreen(
        state = state,
        onAction = viewModel::trySendAction,
        onConfirmNavigateBack = viewModel::confirmNavigateBack
    )
}
