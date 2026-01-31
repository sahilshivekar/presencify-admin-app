package edu.watumull.presencify.feature.academics.course_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CourseDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditCourse: (String) -> Unit = {},
) {
    val viewModel: CourseDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is CourseDetailsEvent.NavigateBack -> onNavigateBack()
            is CourseDetailsEvent.NavigateToEditCourse -> onNavigateToEditCourse(event.courseId)
        }
    }

    CourseDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
