package edu.watumull.presencify.feature.academics.scheme_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SchemeDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditScheme: (String) -> Unit = {},
) {
    val viewModel: SchemeDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SchemeDetailsEvent.NavigateBack -> onNavigateBack()
            is SchemeDetailsEvent.NavigateToEditScheme -> onNavigateToEditScheme(event.schemeId)
        }
    }

    SchemeDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
