package edu.watumull.presencify.feature.academics.add_edit_semester

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
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
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.utils.toReadableString
import kotlinx.datetime.LocalDate

@Composable
fun AddEditSemesterScreen(
    state: AddEditSemesterState,
    onAction: (AddEditSemesterAction) -> Unit,
    onConfirmNavigateBack: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddEditSemesterAction.BackButtonClick) },
        topBarTitle = if (state.isEditMode) "Edit Semester" else "Add Semester",
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
                    text = "Semester Details",
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
                    onSelectItem = { onAction(AddEditSemesterAction.UpdateSemesterNumber(it)) },
                    label = "Semester Number *",
                    itemToString = { it.toDisplayLabel() },
                    expanded = state.isSemesterNumberDropdownOpen,
                    onDropDownVisibilityChanged = { onAction(AddEditSemesterAction.ChangeSemesterNumberDropDownVisibility(it)) },
                    supportingText = state.semesterNumberError,
                    enabled = !state.isLoading && !state.isSubmitting && !state.isEditMode,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // Academic Start Year
                PresencifyTextField(
                    value = state.academicStartYear,
                    onValueChange = { onAction(AddEditSemesterAction.UpdateAcademicStartYear(it)) },
                    label = "Academic Start Year *",
                    supportingText = state.academicStartYearError,
                    isError = state.academicStartYearError != null,
                    enabled = !state.isLoading && !state.isSubmitting && !state.isEditMode,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // Academic End Year
                PresencifyTextField(
                    value = state.academicEndYear,
                    onValueChange = { onAction(AddEditSemesterAction.UpdateAcademicEndYear(it)) },
                    label = "Academic End Year *",
                    supportingText = state.academicEndYearError,
                    isError = state.academicEndYearError != null,
                    enabled = !state.isLoading && !state.isSubmitting && !state.isEditMode,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // Start Date Picker
                PresencifyTextField(
                    value = state.startDate?.toReadableString() ?: "",
                    onValueChange = {},
                    label = "Start Date *",
                    supportingText = state.startDateError,
                    isError = state.startDateError != null,
                    enabled = !state.isLoading && !state.isSubmitting,
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(AddEditSemesterAction.ChangeStartDatePickerVisibility(true)) },
                    trailingIcon = {
                        IconButton(onClick = { onAction(AddEditSemesterAction.ChangeStartDatePickerVisibility(true)) }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select Start Date",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )

                Spacer(Modifier.height(16.dp))

                // End Date Picker
                PresencifyTextField(
                    value = state.endDate?.toReadableString() ?: "",
                    onValueChange = {},
                    label = "End Date *",
                    supportingText = state.endDateError,
                    isError = state.endDateError != null,
                    enabled = !state.isLoading && !state.isSubmitting,
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(AddEditSemesterAction.ChangeEndDatePickerVisibility(true)) },
                    trailingIcon = {
                        IconButton(onClick = { onAction(AddEditSemesterAction.ChangeEndDatePickerVisibility(true)) }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select End Date",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )

                Spacer(Modifier.height(16.dp))

                // Branch Dropdown
                PresencifyDropDownMenuBox<Branch>(
                    value = state.branchOptions.find { it.id == state.selectedBranchId }?.abbreviation ?: "",
                    options = state.branchOptions,
                    onSelectItem = { onAction(AddEditSemesterAction.UpdateSelectedBranch(it.id)) },
                    label = "Branch *",
                    itemToString = { it.toDisplayLabel() },
                    expanded = state.isBranchDropdownOpen,
                    onDropDownVisibilityChanged = { onAction(AddEditSemesterAction.ChangeBranchDropDownVisibility(it)) },
                    supportingText = state.branchError,
                    enabled = !state.isLoading && !state.isSubmitting && !state.isEditMode,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // Scheme Dropdown
                PresencifyDropDownMenuBox<Scheme>(
                    value = state.schemeOptions.find { it.id == state.selectedSchemeId }?.name ?: "",
                    options = state.schemeOptions,
                    onSelectItem = { onAction(AddEditSemesterAction.UpdateSelectedScheme(it.id)) },
                    label = "Scheme *",
                    itemToString = { it.toDisplayLabel() },
                    expanded = state.isSchemeDropdownOpen,
                    onDropDownVisibilityChanged = { onAction(AddEditSemesterAction.ChangeSchemeDropDownVisibility(it)) },
                    supportingText = state.schemeError,
                    enabled = !state.isLoading && !state.isSubmitting && !state.isEditMode,
                    modifier = Modifier.fillMaxWidth()
                )

                // Optional Courses Section
                if (state.optionalCourseGroups.isNotEmpty()) {
                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = "Optional Courses",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )

                    // Dynamically create a dropdown for each optional course group
                    state.optionalCourseGroups.keys.sorted().forEach { optionalCourse ->
                        val courses = state.optionalCourseGroups[optionalCourse] ?: emptyList()
                        val selectedCourseId = state.selectedOptionalCourses[optionalCourse]
                        val selectedCourse = courses.find { it.id == selectedCourseId }
                        val isDropdownOpen = state.openOptionalDropdowns.contains(optionalCourse)

                        PresencifyDropDownMenuBox<edu.watumull.presencify.core.domain.model.academics.Course>(
                            value = selectedCourse?.toDisplayLabel() ?: "",
                            options = courses,
                            onSelectItem = { course ->
                                onAction(AddEditSemesterAction.SelectOptionalCourse(optionalCourse, course.id))
                            },
                            label = "$optionalCourse *",
                            itemToString = { it.toDisplayLabel() },
                            expanded = isDropdownOpen,
                            onDropDownVisibilityChanged = { isVisible ->
                                onAction(AddEditSemesterAction.ChangeOptionalCourseDropdownVisibility(optionalCourse, isVisible))
                            },
                            enabled = !state.isLoading && !state.isSubmitting && !state.isFetchingOptionalCourses,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(16.dp))
                    }
                }

                // Show loading indicator when fetching optional courses
                if (state.isFetchingOptionalCourses) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(Modifier.height(24.dp))

                PresencifyButton(
                    onClick = { onAction(AddEditSemesterAction.SubmitClick) },
                    text = if (state.isEditMode) "Update Semester" else "Add Semester",
                    isLoading = state.isSubmitting,
                    enabled = !state.isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Date Picker Dialogs
    if (state.isStartDatePickerVisible) {
        DatePickerDialog(
            selectedDate = state.startDate,
            onDateSelected = { onAction(AddEditSemesterAction.UpdateStartDate(it)) },
            onDismiss = { onAction(AddEditSemesterAction.ChangeStartDatePickerVisibility(false)) }
        )
    }

    if (state.isEndDatePickerVisible) {
        DatePickerDialog(
            selectedDate = state.endDate,
            onDateSelected = { onAction(AddEditSemesterAction.UpdateEndDate(it)) },
            onDismiss = { onAction(AddEditSemesterAction.ChangeEndDatePickerVisibility(false)) }
        )
    }

    // Alert Dialogs
    state.dialogState?.let { dialogState ->
        PresencifyAlertDialog(
            isVisible = dialogState.isVisible,
            dialogType = dialogState.dialogType,
            title = dialogState.title,
            message = dialogState.message?.asString() ?: "",
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES -> onConfirmNavigateBack()
                    DialogIntention.GENERIC -> onAction(AddEditSemesterAction.DismissDialog)
                }
            },
            onDismiss = { onAction(AddEditSemesterAction.DismissDialog) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.toEpochDays()?.let { it * 24 * 60 * 60 * 1000L }
    )

    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val epochDays = millis / (24 * 60 * 60 * 1000)
                        val selectedDateNew = LocalDate.fromEpochDays(epochDays.toInt())
                        onDateSelected(selectedDateNew)
                    }
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

