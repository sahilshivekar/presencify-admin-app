package edu.watumull.presencify.feature.academics.search_division

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchDivisionRoot(
    onNavigateBack: () -> Unit,
    onNavigateToDivisionDetails: (String) -> Unit = {},
    onNavigateToAddEditDivision: (String?) -> Unit = {},
) {
    val viewModel: SearchDivisionViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SearchDivisionEvent.NavigateBack -> onNavigateBack()
            is SearchDivisionEvent.NavigateToDivisionDetails -> {
                onNavigateToDivisionDetails(event.divisionId)
            }
            is SearchDivisionEvent.NavigateToAddEditDivision -> {
                onNavigateToAddEditDivision(null)
            }
        }
    }

    SearchDivisionScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
