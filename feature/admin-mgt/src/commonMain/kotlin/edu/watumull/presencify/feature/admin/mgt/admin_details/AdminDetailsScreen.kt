package edu.watumull.presencify.feature.admin.mgt.admin_details

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.watumull.presencify.core.design.systems.components.*
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDetailsScreen(
    state: AdminDetailsState,
    onAction: (AdminDetailsAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AdminDetailsAction.ClickBackButton) },
        topBarTitle = "Admin Details",
    ) { paddingValues ->
        when (state.viewState) {
            is AdminDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is AdminDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is AdminDetailsState.ViewState.Content -> {
                AdminDetailsScreenContent(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }

    // Dialog handling
    state.dialogState?.let { dialogState ->
        if (dialogState.message != null) {
            PresencifyAlertDialog(
                isVisible = dialogState.isVisible,
                dialogType = dialogState.dialogType,
                title = dialogState.title,
                message = dialogState.message.asString(),
                onDismiss = { onAction(AdminDetailsAction.DismissDialog) },
                onConfirm = when (dialogState.dialogIntention) {
                    DialogIntention.REMOVE_ACCOUNT_CONFIRMATION -> {
                        { onAction(AdminDetailsAction.ConfirmRemoveAccount) }
                    }
                    else -> null
                }
            )
        }
    }
}

@Composable
private fun AdminDetailsScreenContent(
    state: AdminDetailsState,
    onAction: (AdminDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            verticalArrangement = Arrangement.Top
        ) {
            // Profile Icon
            Spacer(modifier = Modifier.height(16.dp))
            Icon(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            // Admin Details Container
            AdminDetailsContainer(
                state = state,
                onAction = onAction
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Container
            PasswordContainer(
                state = state,
                onAction = onAction
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Account Settings Container
            AccountSettingsContainer(
                state = state,
                onAction = onAction
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AdminDetailsContainer(
    state: AdminDetailsState,
    onAction: (AdminDetailsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val emailFocusRequester = remember { FocusRequester() }

    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = modifier
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Admin Details",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = { onAction(AdminDetailsAction.ClickEditDetails) },
                enabled = !state.isSendingVerificationCode &&
                         !state.isLoggingOut &&
                         !state.isRemovingAccount &&
                         !state.isUpdatingDetails
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Details",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PresencifyTextField(
            value = if (state.isEditingDetails) state.editableUsername else state.orgUsername,
            onValueChange = { onAction(AdminDetailsAction.ChangeUsername(it)) },
            isError = state.usernameError != null,
            supportingText = state.usernameError,
            label = "Username",
            enabled = state.isEditingDetails &&
                     !state.isSendingVerificationCode &&
                     !state.isLoggingOut &&
                     !state.isRemovingAccount &&
                     !state.isUpdatingDetails,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() })
        )

        Spacer(modifier = Modifier.height(8.dp))

        PresencifyTextField(
            value = if (state.isEditingDetails) state.editableEmail else state.orgEmail,
            onValueChange = { onAction(AdminDetailsAction.ChangeEmail(it)) },
            isError = state.emailError != null,
            supportingText = state.emailError,
            label = "Email",
            enabled = state.isEditingDetails &&
                     !state.isSendingVerificationCode &&
                     !state.isLoggingOut &&
                     !state.isRemovingAccount &&
                     !state.isUpdatingDetails,
            modifier = Modifier
                .focusRequester(emailFocusRequester)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onAction(AdminDetailsAction.ClickUpdateDetails) })
        )

        AnimatedVisibility(
            visible = state.isEditingDetails,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PresencifyOutlinedButton(
                        onClick = { onAction(AdminDetailsAction.ClickCancelEditingDetails) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        enabled = !state.isSendingVerificationCode &&
                                 !state.isLoggingOut &&
                                 !state.isRemovingAccount &&
                                 !state.isUpdatingDetails,
                        text = "Cancel"
                    )
                    PresencifyButton(
                        onClick = { onAction(AdminDetailsAction.ClickUpdateDetails) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        text = "Update",
                        isLoading = state.isUpdatingDetails,
                        enabled = !state.isSendingVerificationCode &&
                                 !state.isLoggingOut &&
                                 !state.isRemovingAccount
                    )
                }
            }
        }
    }
}

@Composable
private fun PasswordContainer(
    state: AdminDetailsState,
    onAction: (AdminDetailsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Password",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = { onAction(AdminDetailsAction.ClickUpdatePassword) },
                enabled = !state.isSendingVerificationCode &&
                         !state.isLoggingOut &&
                         !state.isRemovingAccount &&
                         !state.isUpdatingDetails
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Update Password",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PresencifyTextField(
            value = "........",
            onValueChange = {},
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            enabled = false
        )
    }
}

@Composable
private fun AccountSettingsContainer(
    state: AdminDetailsState,
    onAction: (AdminDetailsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Account settings",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Verify Email (only show if not verified)
        if (state.isVerified == false) {
            ListItem(
                headlineContent = {
                    Text(
                        text = "Verify email address",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                trailingContent = {
                    if (state.isSendingVerificationCode) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.clickable(
                    enabled = !state.isSendingVerificationCode &&
                             !state.isLoggingOut &&
                             !state.isRemovingAccount &&
                             !state.isUpdatingDetails
                ) {
                    onAction(AdminDetailsAction.ClickVerifyEmail)
                }
            )
            HorizontalDivider()
        }

        // Add Admin
        ListItem(
            headlineContent = {
                Text(
                    text = "Add admin",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.clickable(
                enabled = !state.isSendingVerificationCode &&
                         !state.isLoggingOut &&
                         !state.isRemovingAccount &&
                         !state.isUpdatingDetails
            ) {
                onAction(AdminDetailsAction.ClickAddAdmin)
            }
        )
        HorizontalDivider()

        // Log out
        ListItem(
            headlineContent = {
                Text(
                    text = "Log out",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            trailingContent = {
                if (state.isLoggingOut) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier.clickable(
                enabled = !state.isSendingVerificationCode &&
                         !state.isLoggingOut &&
                         !state.isRemovingAccount &&
                         !state.isUpdatingDetails
            ) {
                onAction(AdminDetailsAction.ClickLogout)
            }
        )
        HorizontalDivider()

        // Remove Account
        ListItem(
            headlineContent = {
                Text(
                    text = "Remove account",
                    color = MaterialTheme.colorScheme.error
                )
            },
            trailingContent = {
                if (state.isRemovingAccount) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier.clickable(
                enabled = !state.isSendingVerificationCode &&
                         !state.isLoggingOut &&
                         !state.isRemovingAccount &&
                         !state.isUpdatingDetails
            ) {
                onAction(AdminDetailsAction.ClickRemoveAccount)
            }
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun AdminDetailsScreenPreview() {
    AdminDetailsScreen(
        state = AdminDetailsState(
            orgEmail = "admin@example.com",
            orgUsername = "admin",
            editableEmail = "admin@example.com",
            editableUsername = "admin",
            viewState = AdminDetailsState.ViewState.Content,
            isVerified = false
        ),
        onAction = {}
    )
}
