package edu.watumull.presencify.feature.academics.branch_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyDefaultLoadingScreen
import edu.watumull.presencify.core.design.systems.components.PresencifyNoResultsIndicator
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyTextButton
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.components.BranchListItem

@Composable
fun BranchDetailsScreen(
    state: BranchDetailsState,
    onAction: (BranchDetailsAction) -> Unit,
    onConfirmRemove: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(BranchDetailsAction.BackButtonClick) },
        topBarTitle = "Branch Details",
    ) { paddingValues ->
        when (state.viewState) {
            is BranchDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is BranchDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is BranchDetailsState.ViewState.Content -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                    ) {
                        state.branch?.let { branch ->
                            BranchListItem(
                                name = branch.name,
                                abbreviation = branch.abbreviation,
                                onClick = null,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PresencifyTextButton(
                                onClick = { onAction(BranchDetailsAction.EditBranchClick) },
                                enabled = !state.isRemovingBranch
                            ) {
                                androidx.compose.material3.Text(
                                    text = "Edit branch",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            PresencifyTextButton(
                                onClick = { onAction(BranchDetailsAction.RemoveBranchClick) },
                                enabled = !state.isRemovingBranch
                            ) {
                                if (state.isRemovingBranch) {
                                    androidx.compose.material3.CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                    )
                                } else {
                                    androidx.compose.material3.Text(
                                        text = "Remove branch",
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    state.dialogState?.let { dialogState ->
        PresencifyAlertDialog(
            isVisible = dialogState.isVisible,
            dialogType = dialogState.dialogType,
            title = dialogState.title,
            message = dialogState.message?.asString() ?: "",
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.CONFIRM_REMOVE_BRANCH -> onConfirmRemove()
                    DialogIntention.GENERIC -> onAction(BranchDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(BranchDetailsAction.DismissDialog) }
        )
    }
}
