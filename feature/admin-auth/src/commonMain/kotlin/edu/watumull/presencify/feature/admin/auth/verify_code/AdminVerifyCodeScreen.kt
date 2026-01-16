package edu.watumull.presencify.feature.admin.auth.verify_code

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyButton
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyTextField
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminVerifyCodeScreen(
    state: AdminVerifyCodeState,
    onAction: (AdminVerifyCodeAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AdminVerifyCodeAction.ClickBackButton) },
        topBarTitle = "Verify Code",
    ) { paddingValues ->
        AdminVerifyCodeScreenContent(
            state = state,
            onAction = onAction,
            modifier = Modifier.padding(paddingValues)
        )
    }

    // Dialog handling
    state.dialogState?.let { dialogState ->
        if (dialogState.message != null) {
            PresencifyAlertDialog(
                isVisible = dialogState.isVisible,
                dialogType = dialogState.dialogType,
                title = dialogState.title,
                message = dialogState.message.asString(),
                onDismiss = { onAction(AdminVerifyCodeAction.DismissDialog) }
            )
        }
    }
}

@Composable
private fun AdminVerifyCodeScreenContent(
    state: AdminVerifyCodeState,
    onAction: (AdminVerifyCodeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            // Description
            Text(
                text = "Enter the six digit verification code sent to your email address ${state.email}" +
                        "\n\n" +
                        "(Code will be invalid after 5 minutes)",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Verification Code TextField
            PresencifyTextField(
                value = state.code,
                onValueChange = { code ->
                    onAction(AdminVerifyCodeAction.ChangeCode(code))
                },
                label = "Verification Code",
                placeholder = { Text("Enter 6-digit code") },
                enabled = !state.isLoading,
                isError = state.codeError != null,
                supportingText = state.codeError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        onAction(AdminVerifyCodeAction.ClickVerifyCode)
                    }
                )
            )
        }

        // Verify Code Button
        PresencifyButton(
            onClick = { onAction(AdminVerifyCodeAction.ClickVerifyCode) },
            enabled = !state.isLoading,
            isLoading = state.isLoading,
            text = "Verify Code"
        )
    }
}
