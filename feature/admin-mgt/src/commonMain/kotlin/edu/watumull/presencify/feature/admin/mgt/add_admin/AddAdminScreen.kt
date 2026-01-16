package edu.watumull.presencify.feature.admin.mgt.add_admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyButton
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyTextField
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAdminScreen(
    state: AddAdminState,
    onAction: (AddAdminAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddAdminAction.ClickBackButton) },
        topBarTitle = "Add Admin",
    ) { paddingValues ->
        AddAdminScreenContent(
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
                onDismiss = { onAction(AddAdminAction.DismissDialog) }
            )
        }
    }
}

@Composable
private fun AddAdminScreenContent(
    state: AddAdminState,
    onAction: (AddAdminAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Fill in necessary details to add another admin",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                PresencifyTextField(
                    value = state.username,
                    onValueChange = { onAction(AddAdminAction.ChangeUsername(it)) },
                    isError = state.usernameError != null,
                    supportingText = state.usernameError,
                    label = "Username",
                    enabled = !state.isAdding,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { emailFocusRequester.requestFocus() }
                    )
                )

                PresencifyTextField(
                    value = state.email,
                    onValueChange = { onAction(AddAdminAction.ChangeEmail(it)) },
                    isError = state.emailError != null,
                    supportingText = state.emailError,
                    label = "Email",
                    enabled = !state.isAdding,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { passwordFocusRequester.requestFocus() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(emailFocusRequester)
                )

                PresencifyTextField(
                    value = state.password,
                    onValueChange = { onAction(AddAdminAction.ChangePassword(it)) },
                    isError = state.passwordError != null,
                    supportingText = state.passwordError,
                    label = "Password",
                    enabled = !state.isAdding,
                    visualTransformation = if (state.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(AddAdminAction.TogglePasswordVisibility) },
                            enabled = !state.isAdding
                        ) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = if (state.isPasswordVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { confirmPasswordFocusRequester.requestFocus() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester)
                )

                PresencifyTextField(
                    value = state.confirmPassword,
                    onValueChange = { onAction(AddAdminAction.ChangeConfirmPassword(it)) },
                    isError = state.confirmPasswordError != null,
                    supportingText = state.confirmPasswordError,
                    label = "Confirm password",
                    enabled = !state.isAdding,
                    visualTransformation = if (state.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onAction(AddAdminAction.ClickAddAdmin) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(confirmPasswordFocusRequester)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PresencifyButton(
                onClick = { onAction(AddAdminAction.ClickAddAdmin) },
                enabled = !state.isAdding,
                isLoading = state.isAdding,
                text = "Add admin"
            )
        }
    }
}

@Preview
@Composable
private fun AddAdminScreenPreview() {
    AddAdminScreen(
        state = AddAdminState(
            username = "admin",
            email = "admin@example.com",
            password = "password123",
            confirmPassword = "password123",
            emailError = null,
            usernameError = null,
            passwordError = null,
            confirmPasswordError = null,
            isPasswordVisible = false,
            isAdding = false
        ),
        onAction = {}
    )
}
