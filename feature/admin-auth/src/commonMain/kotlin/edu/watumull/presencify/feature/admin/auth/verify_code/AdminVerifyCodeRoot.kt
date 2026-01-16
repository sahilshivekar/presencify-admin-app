package edu.watumull.presencify.feature.admin.auth.verify_code

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.watumull.presencify.core.presentation.utils.EventsEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminVerifyCodeRoot(
    onBackButtonClick: () -> Unit,
    onCodeVerified: () -> Unit,
) {
    val viewModel: AdminVerifyCodeViewModel = koinViewModel()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    EventsEffect(viewModel.eventFlow) { event ->
        when (event) {
            is AdminVerifyCodeEvent.NavigateBack -> onBackButtonClick()
            is AdminVerifyCodeEvent.NavigateToHome -> onCodeVerified()
        }
    }

    AdminVerifyCodeScreen(
        state = state,
        onAction = viewModel::trySendAction
    )
}
