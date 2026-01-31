package edu.watumull.presencify.feature.users.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UsersDashboardRoot(
    onNavigateToSearchStudents: () -> Unit,
    onNavigateToSearchTeachers: () -> Unit,
    onNavigateToAssignUnassignSemester: () -> Unit,
    onNavigateToAssignUnassignDivision: () -> Unit,
    onNavigateToAssignUnassignBatch: () -> Unit,
    onNavigateToModifyDivision: () -> Unit,
    onNavigateToModifyBatch: () -> Unit,
    onNavigateToAddToDropout: () -> Unit,
    onNavigateToRemoveFromDropout: () -> Unit,
    viewModel: UsersDashboardViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            UsersDashboardEvent.NavigateToSearchStudents -> onNavigateToSearchStudents()
            UsersDashboardEvent.NavigateToSearchTeachers -> onNavigateToSearchTeachers()

            UsersDashboardEvent.NavigateToAssignUnassignSemester -> onNavigateToAssignUnassignSemester()
            UsersDashboardEvent.NavigateToAssignUnassignDivision -> onNavigateToAssignUnassignDivision()
            UsersDashboardEvent.NavigateToAssignUnassignBatch -> onNavigateToAssignUnassignBatch()

            UsersDashboardEvent.NavigateToModifyDivision -> onNavigateToModifyDivision()
            UsersDashboardEvent.NavigateToModifyBatch -> onNavigateToModifyBatch()

            UsersDashboardEvent.NavigateToAddToDropout -> onNavigateToAddToDropout()
            UsersDashboardEvent.NavigateToRemoveFromDropout -> onNavigateToRemoveFromDropout()
        }
    }

    UsersDashboardScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}