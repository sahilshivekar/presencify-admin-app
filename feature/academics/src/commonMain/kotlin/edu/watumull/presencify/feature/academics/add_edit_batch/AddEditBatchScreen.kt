package edu.watumull.presencify.feature.academics.add_edit_batch

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
import edu.watumull.presencify.core.domain.model.academics.Division
import edu.watumull.presencify.core.presentation.UiConstants

@Composable
fun AddEditBatchScreen(
    state: AddEditBatchState,
    onAction: (AddEditBatchAction) -> Unit,
    onConfirmNavigateBack: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddEditBatchAction.BackButtonClick) },
        topBarTitle = if (state.isEditMode) "Edit Batch" else "Add Batch",
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
                        onSelectItem = { onAction(AddEditBatchAction.UpdateSemesterNumber(it)) },
                        label = "Semester Number *",
                        itemToString = { it.toDisplayLabel() },
                        expanded = state.isSemesterNumberDropdownOpen,
                        onDropDownVisibilityChanged = { onAction(AddEditBatchAction.ChangeSemesterNumberDropDownVisibility(it)) },
                        supportingText = state.semesterNumberError,
                        enabled = !state.isLoading && !state.isSubmitting && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Academic Start Year
                    PresencifyTextField(
                        value = state.academicStartYear,
                        onValueChange = { onAction(AddEditBatchAction.UpdateAcademicStartYear(it)) },
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
                        onValueChange = { onAction(AddEditBatchAction.UpdateAcademicEndYear(it)) },
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
                        onSelectItem = { onAction(AddEditBatchAction.UpdateSelectedBranch(it.id)) },
                        label = "Branch *",
                        itemToString = { it.toDisplayLabel() },
                        expanded = state.isBranchDropdownOpen,
                        onDropDownVisibilityChanged = { onAction(AddEditBatchAction.ChangeBranchDropDownVisibility(it)) },
                        supportingText = state.branchError,
                        enabled = !state.isLoading && !state.isSubmitting && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    // Find Divisions Button
                    PresencifyButton(
                        onClick = { onAction(AddEditBatchAction.FindDivisionsClick) },
                        text = "Find Divisions",
                        isLoading = state.isLoading,
                        enabled = !state.isLoading && !state.showDivisionInput,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Step 2: Division Selection (shown after divisions found)
                if (!state.isEditMode && state.showDivisionInput && state.foundDivisions.isNotEmpty()) {
                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = "Step 2: Select Division",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    PresencifyDropDownMenuBox<Division>(
                        value = state.foundDivisions.find { it.id == state.selectedDivisionId }?.divisionCode ?: "",
                        options = state.foundDivisions,
                        onSelectItem = { onAction(AddEditBatchAction.UpdateSelectedDivision(it.id)) },
                        label = "Division *",
                        itemToString = { it.divisionCode },
                        expanded = state.isDivisionDropdownOpen,
                        onDropDownVisibilityChanged = { onAction(AddEditBatchAction.ChangeDivisionDropDownVisibility(it)) },
                        supportingText = state.divisionError,
                        enabled = !state.isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Step 3: Batch Code (shown after division is selected)
                if (state.showBatchInput) {
                    Spacer(Modifier.height(32.dp))

                    if (!state.isEditMode) {
                        Text(
                            text = "Step 3: Add Batch Code",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }

                    // Display Division Code and Semester Details
                    state.foundDivision?.let { division ->
                        state.foundSemester?.let { semester ->
                            Text(
                                text = "Division: ${division.divisionCode} | Semester: ${semester.semesterNumber.value} | Branch: ${semester.branch?.name} | Academic Year: ${semester.academicStartYear} - ${semester.academicEndYear}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                            )
                        }
                    }

                    PresencifyTextField(
                        value = state.batchCode,
                        onValueChange = { onAction(AddEditBatchAction.UpdateBatchCode(it)) },
                        label = "Batch Code *",
                        supportingText = state.batchCodeError,
                        isError = state.batchCodeError != null,
                        enabled = !state.isSubmitting,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    PresencifyButton(
                        onClick = { onAction(AddEditBatchAction.SubmitClick) },
                        text = if (state.isEditMode) "Update Batch" else "Add Batch",
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
                    DialogIntention.GENERIC -> onAction(AddEditBatchAction.DismissDialog)
                }
            },
            onDismiss = { onAction(AddEditBatchAction.DismissDialog) }
        )
    }
}
