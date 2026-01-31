package edu.watumull.presencify.feature.onboarding.select_role

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.Res
import edu.watumull.presencify.core.design.systems.baseline_admin_panel_settings_24
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem
import edu.watumull.presencify.core.design.systems.components.PresencifyNoResultsIndicator
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyLoadingDialog
import edu.watumull.presencify.core.design.systems.presencify_logo_circle_svg
import edu.watumull.presencify.core.presentation.UiConstants
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRoleScreen(
    state: SelectRoleState,
    onAction: (SelectRoleAction) -> Unit,
) {
    PresencifyScaffold(
        // Back functionality disabled: Empty lambda passed, and topBarTitle = null hides the UI
        backPress = { },
        topBarTitle = null,
    ) { paddingValues ->
        when (state.viewState) {
            is SelectRoleState.ViewState.Loading -> {
                PresencifyLoadingDialog(isVisible = true)
            }

            is SelectRoleState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message)
            }

            is SelectRoleState.ViewState.Content -> {
                SelectRoleScreenContent(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }

    state.dialogState?.let { dialogState ->
        PresencifyAlertDialog(
            isVisible = dialogState.isVisible,
            dialogType = dialogState.dialogType,
            title = dialogState.title,
            message = dialogState.message,
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.GENERIC -> {}
                }
            },
            onDismiss = {
                onAction(SelectRoleAction.DismissDialog)
            }
        )
    }
}

@Preview
@Composable
private fun SelectRoleScreenPreview() {
    SelectRoleScreen(
        state = SelectRoleState(viewState = SelectRoleState.ViewState.Content),
        onAction = {}
    )
}

@Composable
private fun SelectRoleScreenContent(
    state: SelectRoleState,
    onAction: (SelectRoleAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Logo
            Image(
                painter = painterResource(Res.drawable.presencify_logo_circle_svg),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            // 2. Welcome Text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome to Presencify",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Select a role to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            // 3. Role List Items
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.widthIn(max = 400.dp)
            ) {
                RoleSelectionItem(
                    text = "Student",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Student Role",
                        )
                    },
                    onClick = { onAction(SelectRoleAction.OnStudentSelected) }
                )

                RoleSelectionItem(
                    text = "Teacher",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Teacher Role"
                        )
                    },
                    onClick = { onAction(SelectRoleAction.OnTeacherSelected) }
                )

                RoleSelectionItem(
                    text = "Admin",
                    icon = {
                        Icon(
                            painter = painterResource(Res.drawable.baseline_admin_panel_settings_24),
                            contentDescription = "Admin Role"
                        )
                    },
                    onClick = { onAction(SelectRoleAction.OnAdminSelected) }
                )
            }
        }
    }
}

@Composable
private fun RoleSelectionItem(
    text: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    PresencifyListItem(
        headlineContent = { Text(text = text, style = MaterialTheme.typography.titleSmall) },
        leadingContent = icon,
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Select",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        onClick = onClick
    )
}