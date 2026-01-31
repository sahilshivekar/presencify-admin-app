package edu.watumull.presencify.feature.academics.division_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DivisionDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditDivision: (String) -> Unit = {},
) {
    val viewModel: DivisionDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is DivisionDetailsEvent.NavigateBack -> onNavigateBack()
            is DivisionDetailsEvent.NavigateToEditDivision -> onNavigateToEditDivision(event.divisionId)
        }
    }

    DivisionDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
