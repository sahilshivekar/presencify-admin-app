package edu.watumull.presencify.feature.users.teacher_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeacherDetailsRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEditTeacher: (String) -> Unit,
    onNavigateToAssignUnassignCourses: (String) -> Unit,
) {
    val viewModel: TeacherDetailsViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is TeacherDetailsEvent.NavigateBack -> onNavigateBack()
            is TeacherDetailsEvent.NavigateToEditTeacher -> onNavigateToEditTeacher(event.teacherId)
            is TeacherDetailsEvent.NavigateToAssignUnassignCourses -> onNavigateToAssignUnassignCourses(event.teacherId)
        }
    }

    TeacherDetailsScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}

