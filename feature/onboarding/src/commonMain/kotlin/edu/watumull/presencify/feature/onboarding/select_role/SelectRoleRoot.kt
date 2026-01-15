package edu.watumull.presencify.feature.onboarding.select_role

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SelectRoleRoot(
    onNavigateToStudentLogin: () -> Unit,
    onNavigateToTeacherLogin: () -> Unit,
    onNavigateToAdminLogin: () -> Unit,
) {
    val viewModel: SelectRoleViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            SelectRoleEvent.NavigateToAdminLogin -> onNavigateToAdminLogin()
            SelectRoleEvent.NavigateToStudentLogin -> onNavigateToStudentLogin()
            SelectRoleEvent.NavigateToTeacherLogin -> onNavigateToTeacherLogin()
        }
    }

    SelectRoleScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}