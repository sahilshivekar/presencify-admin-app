package edu.watumull.presencify.feature.academics.add_edit_scheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditSchemeRoot(
    onNavigateBack: () -> Unit,
) {
    val viewModel: AddEditSchemeViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is AddEditSchemeEvent.NavigateBack -> onNavigateBack()
        }
    }

    AddEditSchemeScreen(
        state = state,
        onAction = viewModel::trySendAction,
        onConfirmNavigateBack = viewModel::confirmNavigateBack
    )
}
