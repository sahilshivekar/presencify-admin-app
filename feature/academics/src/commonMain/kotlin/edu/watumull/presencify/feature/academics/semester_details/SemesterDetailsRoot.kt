package edu.watumull.presencify.feature.academics.semester_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SemesterDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditSemester: (String) -> Unit = {},
) {
    val viewModel: SemesterDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SemesterDetailsEvent.NavigateBack -> onNavigateBack()
            is SemesterDetailsEvent.NavigateToEditSemester -> onNavigateToEditSemester(event.semesterId)
        }
    }

    SemesterDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction,
        onConfirmRemove = viewModel::confirmRemoveSemester
    )
}
