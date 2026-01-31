package edu.watumull.presencify.feature.academics.add_edit_scheme

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
import edu.watumull.presencify.core.design.systems.components.PresencifyDropDownMenuBox
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyTextField
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.domain.model.academics.University
import edu.watumull.presencify.core.presentation.UiConstants

@Composable
fun AddEditSchemeScreen(
    state: AddEditSchemeState,
    onAction: (AddEditSchemeAction) -> Unit,
    onConfirmNavigateBack: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddEditSchemeAction.BackButtonClick) },
        topBarTitle = if (state.isEditMode) "Edit Scheme" else "Add Scheme",
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
                    text = "Scheme Details",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                PresencifyTextField(
                    value = state.name,
                    onValueChange = { onAction(AddEditSchemeAction.UpdateName(it)) },
                    label = "Scheme name *",
                    supportingText = state.nameError,
                    isError = state.nameError != null,
                    enabled = !state.isLoading && !state.isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                PresencifyDropDownMenuBox<University>(
                    value = state.universityOptions.find { it.id == state.selectedUniversityId }?.name ?: "",
                    options = state.universityOptions,
                    onSelectItem = { onAction(AddEditSchemeAction.UpdateSelectedUniversity(it.id)) },
                    label = "University *",
                    itemToString = { it.name },
                    expanded = state.isUniversityDropdownOpen,
                    onDropDownVisibilityChanged = { onAction(AddEditSchemeAction.ChangeUniversityDropDownVisibility(it)) },
                    supportingText = state.universityError,
                    enabled = !state.isLoading && !state.isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))

                PresencifyButton(
                    onClick = { onAction(AddEditSchemeAction.SubmitClick) },
                    text = if (state.isEditMode) "Update Scheme" else "Add Scheme",
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
                    DialogIntention.GENERIC -> onAction(AddEditSchemeAction.DismissDialog)
                }
            },
            onDismiss = { onAction(AddEditSchemeAction.DismissDialog) }
        )
    }
}
