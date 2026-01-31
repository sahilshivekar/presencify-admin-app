package edu.watumull.presencify.feature.academics.search_course

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchCourseRoot(
    onNavigateBack: () -> Unit,
    onNavigateToCourseDetails: (String) -> Unit = {},
    onNavigateToAddEditCourse: (String?) -> Unit = {},
) {
    val viewModel: SearchCourseViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is SearchCourseEvent.NavigateBack -> onNavigateBack()
            is SearchCourseEvent.NavigateToCourseDetails -> {
                onNavigateToCourseDetails(event.courseId)
            }
            is SearchCourseEvent.NavigateBackWithSelection -> {
                // For now, just navigate back
                // In the future, this could pass selected course IDs back to caller
                onNavigateBack()
            }
            is SearchCourseEvent.NavigateToAddEditCourse -> {
                onNavigateToAddEditCourse(null)
            }
        }
    }

    SearchCourseScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
