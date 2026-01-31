package edu.watumull.presencify.feature.academics.add_edit_university

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyButton
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyTextField
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants

@Composable
fun AddEditUniversityScreen(
    state: AddEditUniversityState,
    onAction: (AddEditUniversityAction) -> Unit,
    onConfirmNavigateBack: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddEditUniversityAction.BackButtonClick) },
        topBarTitle = if (state.isEditMode) "Edit University" else "Add University",
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "University Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                PresencifyTextField(
                    value = state.name,
                    onValueChange = { onAction(AddEditUniversityAction.UpdateName(it)) },
                    label = "University name *",
                    supportingText = state.nameError,
                    isError = state.nameError != null,
                    enabled = !state.isLoading && !state.isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                PresencifyTextField(
                    value = state.abbreviation,
                    onValueChange = { onAction(AddEditUniversityAction.UpdateAbbreviation(it)) },
                    label = "Abbreviation *",
                    supportingText = state.abbreviationError,
                    isError = state.abbreviationError != null,
                    enabled = !state.isLoading && !state.isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                PresencifyButton(
                    onClick = { onAction(AddEditUniversityAction.SubmitClick) },
                    text = if (state.isEditMode) "Update University" else "Add University",
                    isLoading = state.isSubmitting,
                    enabled = !state.isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Dialogs
    state.dialogState?.let { dialogState ->
        PresencifyAlertDialog(
            isVisible = dialogState.isVisible,
            dialogType = dialogState.dialogType,
            title = dialogState.title,
            message = dialogState.message?.asString() ?: "",
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES -> onConfirmNavigateBack()
                    DialogIntention.GENERIC -> onAction(AddEditUniversityAction.DismissDialog)
                }
            },
            onDismiss = { onAction(AddEditUniversityAction.DismissDialog) }
        )
    }
}
