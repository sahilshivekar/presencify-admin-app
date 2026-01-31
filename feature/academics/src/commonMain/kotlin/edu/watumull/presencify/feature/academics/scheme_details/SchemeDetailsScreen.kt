package edu.watumull.presencify.feature.academics.scheme_details

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
import edu.watumull.presencify.core.presentation.components.SchemeListItem

@Composable
fun SchemeDetailsScreen(
    state: SchemeDetailsState,
    onAction: (SchemeDetailsAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(SchemeDetailsAction.BackButtonClick) },
        topBarTitle = "Scheme Details",
    ) { paddingValues ->
        when (state.viewState) {
            is SchemeDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is SchemeDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is SchemeDetailsState.ViewState.Content -> {
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
                            .padding(top = 8.dp)
                    ) {
                        state.scheme?.let { scheme ->
                            SchemeListItem(
                                name = scheme.name,
                                onClick = null,
                                modifier = Modifier.fillMaxWidth(),
                                universityName = scheme.university?.name ?: "N/A"
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PresencifyTextButton(
                                onClick = { onAction(SchemeDetailsAction.EditSchemeClick) },
                                enabled = !state.isRemovingScheme
                            ) {
                                androidx.compose.material3.Text(text = "Edit scheme", color = MaterialTheme.colorScheme.primary)
                            }

                            PresencifyTextButton(
                                onClick = { onAction(SchemeDetailsAction.RemoveSchemeClick) },
                                enabled = !state.isRemovingScheme
                            ) {
                                if (state.isRemovingScheme) {
                                    androidx.compose.material3.CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                    )
                                } else {
                                    androidx.compose.material3.Text(text = "Remove scheme", color = MaterialTheme.colorScheme.error)
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
                    DialogIntention.CONFIRM_REMOVE_SCHEME -> onAction(SchemeDetailsAction.ConfirmRemoveScheme)
                    DialogIntention.GENERIC -> onAction(SchemeDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(SchemeDetailsAction.DismissDialog) }
        )
    }
}

