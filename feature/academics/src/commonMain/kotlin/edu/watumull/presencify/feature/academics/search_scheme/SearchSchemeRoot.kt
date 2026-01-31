package edu.watumull.presencify.feature.academics.search_scheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchSchemeRoot(
    onNavigateBack: () -> Unit,
    onNavigateToSchemeDetails: (String) -> Unit = {},
    onNavigateToAddEditScheme: (String?) -> Unit = {},
) {
    val viewModel: SearchSchemeViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SearchSchemeEvent.NavigateBack -> onNavigateBack()
            is SearchSchemeEvent.NavigateToSchemeDetails -> {
                onNavigateToSchemeDetails(event.schemeId)
            }
            is SearchSchemeEvent.NavigateToAddEditScheme -> {
                onNavigateToAddEditScheme(null)
            }
        }
    }

    SearchSchemeScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
