package edu.watumull.presencify.feature.academics.batch_details

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
import edu.watumull.presencify.core.presentation.components.BatchListItem

@Composable
fun BatchDetailsScreen(
    state: BatchDetailsState,
    onAction: (BatchDetailsAction) -> Unit,
    onConfirmRemove: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(BatchDetailsAction.BackButtonClick) },
        topBarTitle = "Batch Details",
    ) { paddingValues ->
        when (state.viewState) {
            is BatchDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is BatchDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is BatchDetailsState.ViewState.Content -> {
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
                        state.batch?.let { batch ->
                            val div = batch.division
                            val sem = div?.semester
                            BatchListItem(
                                batchCode = batch.batchCode,
                                divisionCode = div?.divisionCode ?: "",
                                semesterNumber = sem?.semesterNumber
                                    ?: edu.watumull.presencify.core.domain.enums.SemesterNumber.SEMESTER_1,
                                semesterAcademicStartYear = sem?.academicStartYear ?: 0,
                                semesterAcademicEndYear = sem?.academicEndYear ?: 0,
                                branchAbbreviation = sem?.branch?.abbreviation ?: div?.semester?.branch?.abbreviation ?: "",
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
                                onClick = { onAction(BatchDetailsAction.EditBatchClick) },
                                enabled = !state.isRemovingBatch
                            ) {
                                androidx.compose.material3.Text(
                                    text = "Edit batch",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            PresencifyTextButton(
                                onClick = { onAction(BatchDetailsAction.RemoveBatchClick) },
                                enabled = !state.isRemovingBatch
                            ) {
                                if (state.isRemovingBatch) {
                                    androidx.compose.material3.CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                    )
                                } else {
                                    androidx.compose.material3.Text(
                                        text = "Remove batch",
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
                    DialogIntention.CONFIRM_REMOVE_BATCH -> onConfirmRemove()
                    DialogIntention.GENERIC -> onAction(BatchDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(BatchDetailsAction.DismissDialog) }
        )
    }
}
