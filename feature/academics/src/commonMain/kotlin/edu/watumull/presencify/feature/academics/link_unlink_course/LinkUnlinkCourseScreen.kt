package edu.watumull.presencify.feature.academics.link_unlink_course

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.*
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.presentation.UiConstants

@Composable
fun LinkUnlinkCourseScreen(
    state: LinkUnlinkCourseState,
    onAction: (LinkUnlinkCourseAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(LinkUnlinkCourseAction.BackButtonClick) },
        topBarTitle = "Link/Unlink Courses",
    ) { paddingValues ->
        when (state.viewState) {
            is LinkUnlinkCourseState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is LinkUnlinkCourseState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is LinkUnlinkCourseState.ViewState.Content -> {
                LinkUnlinkCourseScreenContent(
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
            message = dialogState.message.asString(),
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.GENERIC -> {
                        onAction(LinkUnlinkCourseAction.DismissDialog)
                    }
                }
            },
            onDismiss = {
                onAction(LinkUnlinkCourseAction.DismissDialog)
            }
        )
    }
}

@Composable
private fun LinkUnlinkCourseScreenContent(
    state: LinkUnlinkCourseState,
    onAction: (LinkUnlinkCourseAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Instructions
            Text(
                text = "Select a branch and semester to link or unlink courses",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Branch Selection
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Branch",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                PresencifyDropDownMenuBox<Branch>(
                    value = state.selectedBranch?.name ?: "",
                    options = state.branchOptions,
                    onSelectItem = { onAction(LinkUnlinkCourseAction.SelectBranch(it)) },
                    label = "Select Branch",
                    itemToString = { it.name },
                    expanded = state.isBranchDropdownOpen,
                    onDropDownVisibilityChanged = { onAction(LinkUnlinkCourseAction.ChangeBranchDropDownVisibility(it)) },
                    supportingText = state.branchError,
                    enabled = !state.areBranchesLoading,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Semester Selection
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Semester",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                PresencifyDropDownMenuBox<SemesterNumber>(
                    value = state.selectedSemesterNumber?.toDisplayLabel() ?: "",
                    options = state.semesterNumberOptions,
                    onSelectItem = { onAction(LinkUnlinkCourseAction.SelectSemesterNumber(it)) },
                    label = "Select Semester",
                    itemToString = { it.toDisplayLabel() },
                    expanded = state.isSemesterDropdownOpen,
                    onDropDownVisibilityChanged = { onAction(LinkUnlinkCourseAction.ChangeSemesterDropDownVisibility(it)) },
                    supportingText = state.semesterError,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Link Courses Button
            PresencifyButton(
                onClick = { onAction(LinkUnlinkCourseAction.LinkCoursesClick) },
                text = "Link Courses",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}
