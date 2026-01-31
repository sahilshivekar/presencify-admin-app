package edu.watumull.presencify.feature.academics.university_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import edu.watumull.presencify.core.presentation.components.UniversityListItem

@Composable
fun UniversityDetailsScreen(
    state: UniversityDetailsState,
    onAction: (UniversityDetailsAction) -> Unit,
    onConfirmRemove: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(UniversityDetailsAction.BackButtonClick) },
        topBarTitle = "Universities",
        floatingActionButton = {
            if (state.viewState is UniversityDetailsState.ViewState.Content) {
                FloatingActionButton(
                    onClick = { onAction(UniversityDetailsAction.AddUniversityClick) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add University"
                    )
                }
            }
        }
    ) { paddingValues ->
        when (state.viewState) {
            is UniversityDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is UniversityDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is UniversityDetailsState.ViewState.Content -> {
                if (state.universities.isEmpty()) {
                    PresencifyNoResultsIndicator(text = "No universities found")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            items = state.universities,
                            key = { university -> university.id }
                        ) { university ->
                            Column(
                                modifier = Modifier
                                    .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                                    .fillMaxWidth()
                            ) {
                                UniversityListItem(
                                    name = university.name,
                                    abbreviation = university.abbreviation,
                                    onClick = null,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    PresencifyTextButton(
                                        onClick = { onAction(UniversityDetailsAction.EditUniversityClick(university.id)) },
                                        enabled = state.removingUniversityId != university.id
                                    ) {
                                        Text(
                                            text = "Edit",
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    PresencifyTextButton(
                                        onClick = { onAction(UniversityDetailsAction.RemoveUniversityClick(university.id)) },
                                        enabled = state.removingUniversityId != university.id
                                    ) {
                                        if (state.removingUniversityId == university.id) {
                                            CircularProgressIndicator(
                                                color = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(20.dp),
                                                strokeWidth = 2.dp
                                            )
                                        } else {
                                            Text(
                                                text = "Delete",
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
                    DialogIntention.CONFIRM_REMOVE_UNIVERSITY -> onConfirmRemove()
                    DialogIntention.GENERIC -> onAction(UniversityDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(UniversityDetailsAction.DismissDialog) }
        )
    }
}
