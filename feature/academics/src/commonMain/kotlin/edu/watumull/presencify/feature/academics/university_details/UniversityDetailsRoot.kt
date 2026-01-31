package edu.watumull.presencify.feature.academics.university_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UniversityDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToAddUniversity: () -> Unit,
    onNavigateToEditUniversity: (String) -> Unit,
) {
    val viewModel: UniversityDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is UniversityDetailsEvent.NavigateBack -> onNavigateBack()
            is UniversityDetailsEvent.NavigateToAddUniversity -> onNavigateToAddUniversity()
            is UniversityDetailsEvent.NavigateToEditUniversity -> onNavigateToEditUniversity(event.universityId)
        }
    }

    UniversityDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
