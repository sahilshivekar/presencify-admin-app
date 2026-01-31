package edu.watumull.presencify.feature.academics.add_edit_university

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditUniversityRoot(
    onNavigateBack: () -> Unit,
) {
    val viewModel: AddEditUniversityViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is AddEditUniversityEvent.NavigateBack -> onNavigateBack()
        }
    }

    AddEditUniversityScreen(
        state = state,
        onAction = viewModel::trySendAction,
        onConfirmNavigateBack = viewModel::confirmNavigateBack
    )
}
