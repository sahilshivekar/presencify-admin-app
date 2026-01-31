package edu.watumull.presencify.feature.academics.division_details

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
import edu.watumull.presencify.core.presentation.components.DivisionListItem
import kotlinx.collections.immutable.toPersistentList

@Composable
fun DivisionDetailsScreen(
    state: DivisionDetailsState,
    onAction: (DivisionDetailsAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(DivisionDetailsAction.BackButtonClick) },
        topBarTitle = "Division Details",
    ) { paddingValues ->
        when (state.viewState) {
            is DivisionDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is DivisionDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is DivisionDetailsState.ViewState.Content -> {
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
                        state.division?.let { division ->
                            val semester = division.semester
                            val branch = semester?.branch
                            val batchCodes = division.batches?.map { it.batchCode }?.toPersistentList()
                                ?: kotlinx.collections.immutable.persistentListOf()

                            if (semester != null && branch != null) {
                                DivisionListItem(
                                    divisionCode = division.divisionCode,
                                    batchCodes = batchCodes,
                                    semesterNumber = semester.semesterNumber,
                                    semesterAcademicStartYear = semester.academicStartYear,
                                    semesterAcademicEndYear = semester.academicEndYear,
                                    branchAbbreviation = branch.abbreviation,
                                    onClick = {  },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PresencifyTextButton(
                                onClick = { onAction(DivisionDetailsAction.EditDivisionClick) },
                                enabled = !state.isRemovingDivision
                            ) {
                                androidx.compose.material3.Text(
                                    text = "Edit division",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            PresencifyTextButton(
                                onClick = { onAction(DivisionDetailsAction.RemoveDivisionClick) },
                                enabled = !state.isRemovingDivision
                            ) {
                                if (state.isRemovingDivision) {
                                    androidx.compose.material3.CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                    )
                                } else {
                                    androidx.compose.material3.Text(
                                        text = "Remove division",
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
                    DialogIntention.CONFIRM_REMOVE_DIVISION -> onAction(DivisionDetailsAction.ConfirmRemoveDivision)
                    DialogIntention.GENERIC -> onAction(DivisionDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(DivisionDetailsAction.DismissDialog) }
        )
    }
}
