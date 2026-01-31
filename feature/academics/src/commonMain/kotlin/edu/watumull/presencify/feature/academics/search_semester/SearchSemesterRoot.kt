package edu.watumull.presencify.feature.academics.search_semester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchSemesterRoot(
    onNavigateBack: () -> Unit,
    onNavigateToSemesterDetails: (String) -> Unit = {},
    onNavigateToAddEditSemester: (String?) -> Unit = {},
) {
    val viewModel: SearchSemesterViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SearchSemesterEvent.NavigateBack -> onNavigateBack()
            is SearchSemesterEvent.NavigateToSemesterDetails -> {
                onNavigateToSemesterDetails(event.semesterId)
            }
            is SearchSemesterEvent.NavigateToAddEditSemester -> {
                onNavigateToAddEditSemester(null)
            }
        }
    }

    SearchSemesterScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
