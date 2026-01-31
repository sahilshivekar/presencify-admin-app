package edu.watumull.presencify.feature.academics.link_unlink_course

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LinkUnlinkCourseRoot(
    onNavigateBack: () -> Unit,
    onNavigateToSearchCourse: (branchId: String, semesterNumber: Int) -> Unit,
) {
    val viewModel = koinViewModel<LinkUnlinkCourseViewModel>()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is LinkUnlinkCourseEvent.NavigateBack -> {
                onNavigateBack()
            }

            is LinkUnlinkCourseEvent.NavigateToSearchCourse -> {
                onNavigateToSearchCourse(event.branchId, event.semesterNumber)
            }
        }
    }

    LinkUnlinkCourseScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
