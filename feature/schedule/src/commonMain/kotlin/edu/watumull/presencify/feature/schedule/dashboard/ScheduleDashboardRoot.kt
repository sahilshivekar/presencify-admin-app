package edu.watumull.presencify.feature.schedule.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduleDashboardRoot(
    onNavigateToSearchRoom: () -> Unit,
    onNavigateToSearchClass: () -> Unit,
    viewModel: ScheduleDashboardViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            ScheduleDashboardEvent.NavigateToRoom -> onNavigateToSearchRoom()
            ScheduleDashboardEvent.NavigateToClasses -> onNavigateToSearchClass()
        }
    }

    ScheduleDashboardScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}

