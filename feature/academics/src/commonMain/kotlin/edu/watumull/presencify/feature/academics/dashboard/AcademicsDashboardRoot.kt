package edu.watumull.presencify.feature.academics.dashboard


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AcademicsDashboardRoot(
    onNavigateToSearchBranch: () -> Unit,
    onNavigateToSearchScheme: () -> Unit,
    onNavigateToSearchCourse: () -> Unit,
    onNavigateToUniversityDetails: () -> Unit,
    onNavigateToSearchSemester: () -> Unit,
    onNavigateToSearchDivision: () -> Unit,
    onNavigateToSearchBatch: () -> Unit,
    viewModel: AcademicsDashboardViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            AcademicsDashboardEvent.NavigateToSearchBranch -> onNavigateToSearchBranch()
            AcademicsDashboardEvent.NavigateToSearchScheme -> onNavigateToSearchScheme()
            AcademicsDashboardEvent.NavigateToSearchCourse -> onNavigateToSearchCourse()
            AcademicsDashboardEvent.NavigateToUniversityDetails -> onNavigateToUniversityDetails()
            AcademicsDashboardEvent.NavigateToSearchSemester -> onNavigateToSearchSemester()
            AcademicsDashboardEvent.NavigateToSearchDivision -> onNavigateToSearchDivision()
            AcademicsDashboardEvent.NavigateToSearchBatch -> onNavigateToSearchBatch()
        }
    }

    AcademicsDashboardScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}