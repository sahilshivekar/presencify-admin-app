package edu.watumull.presencify.feature.academics.add_edit_division

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
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.presentation.UiConstants

@Composable
fun AddEditDivisionScreen(
    state: AddEditDivisionState,
    onAction: (AddEditDivisionAction) -> Unit,
    onConfirmNavigateBack: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddEditDivisionAction.BackButtonClick) },
        topBarTitle = if (state.isEditMode) "Edit Division" else "Add Division",
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
                if (!state.isEditMode) {
                    // Step 1: Semester Selection
                    Text(
                        text = "Step 1: Select Semester",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Semester Number Dropdown
                    PresencifyDropDownMenuBox<SemesterNumber>(
                        value = state.semesterNumber?.toDisplayLabel() ?: "",
                        options = SemesterNumber.entries,
                        onSelectItem = { onAction(AddEditDivisionAction.UpdateSemesterNumber(it)) },
                        label = "Semester Number *",
                        itemToString = { it.toDisplayLabel() },
                        expanded = state.isSemesterNumberDropdownOpen,
                        onDropDownVisibilityChanged = { onAction(AddEditDivisionAction.ChangeSemesterNumberDropDownVisibility(it)) },
                        supportingText = state.semesterNumberError,
                        enabled = !state.isLoading && !state.isSubmitting && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Academic Start Year
                    PresencifyTextField(
                        value = state.academicStartYear,
                        onValueChange = { onAction(AddEditDivisionAction.UpdateAcademicStartYear(it)) },
                        label = "Academic Start Year *",
                        supportingText = state.academicStartYearError,
                        isError = state.academicStartYearError != null,
                        enabled = !state.isLoading && !state.isSubmitting && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Academic End Year
                    PresencifyTextField(
                        value = state.academicEndYear,
                        onValueChange = { onAction(AddEditDivisionAction.UpdateAcademicEndYear(it)) },
                        label = "Academic End Year *",
                        supportingText = state.academicEndYearError,
                        isError = state.academicEndYearError != null,
                        enabled = !state.isLoading && !state.isSubmitting && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Branch Dropdown
                    PresencifyDropDownMenuBox<Branch>(
                        value = state.branchOptions.find { it.id == state.selectedBranchId }?.abbreviation ?: "",
                        options = state.branchOptions,
                        onSelectItem = { onAction(AddEditDivisionAction.UpdateSelectedBranch(it.id)) },
                        label = "Branch *",
                        itemToString = { it.toDisplayLabel() },
                        expanded = state.isBranchDropdownOpen,
                        onDropDownVisibilityChanged = { onAction(AddEditDivisionAction.ChangeBranchDropDownVisibility(it)) },
                        supportingText = state.branchError,
                        enabled = !state.isLoading && !state.isSubmitting && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    // Find Semester Button
                    PresencifyButton(
                        onClick = { onAction(AddEditDivisionAction.FindSemesterClick) },
                        text = "Find Semester",
                        isLoading = state.isLoading,
                        enabled = !state.isLoading && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (state.showDivisionInput && state.foundSemester != null) {
                    Spacer(Modifier.height(32.dp))

                    if (!state.isEditMode) {
                        Text(
                            text = "Step 2: Add Division Code",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }

                    Text(
                        text = "Semester: ${state.foundSemester.semesterNumber.value} | Branch: ${state.foundSemester.branch?.name} | Academic Year: ${state.foundSemester.academicStartYear} - ${state.foundSemester.academicEndYear}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    PresencifyTextField(
                        value = state.divisionCode,
                        onValueChange = { onAction(AddEditDivisionAction.UpdateDivisionCode(it)) },
                        label = "Division Code *",
                        supportingText = state.divisionCodeError,
                        isError = state.divisionCodeError != null,
                        enabled = !state.isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    PresencifyButton(
                        onClick = { onAction(AddEditDivisionAction.SubmitClick) },
                        text = if (state.isEditMode) "Edit Division" else "Add Division",
                        isLoading = state.isSubmitting,
                        enabled = !state.isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
                    DialogIntention.GENERIC -> onAction(AddEditDivisionAction.DismissDialog)
                }
            },
            onDismiss = { onAction(AddEditDivisionAction.DismissDialog) }
        )
    }
}
