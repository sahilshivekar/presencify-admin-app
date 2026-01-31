package edu.watumull.presencify.feature.users.add_edit_student

import androidx.compose.animation.*
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
import edu.watumull.presencify.core.design.systems.components.*
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.domain.enums.AdmissionType
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.utils.toReadableString
import kotlinx.datetime.LocalDate

@Composable
fun AddEditStudentScreen(
    state: AddEditStudentState,
    onAction: (AddEditStudentAction) -> Unit,
    onConfirmNavigateBack: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(AddEditStudentAction.BackButtonClick) },
        topBarTitle = if (state.isEditMode) "Edit Student" else "Add Student",
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
                AnimatedContent(
                    modifier = Modifier.fillMaxWidth(),
                    targetState = state.currentStep,
                    transitionSpec = {
                        if (targetState.ordinal > initialState.ordinal) {
                            ContentTransform(
                                targetContentEnter = slideInHorizontally { fullWidth -> fullWidth } + fadeIn(),
                                initialContentExit = slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                            )
                        } else {
                            ContentTransform(
                                targetContentEnter = slideInHorizontally { fullWidth -> -fullWidth } + fadeIn(),
                                initialContentExit = slideOutHorizontally { fullWidth -> fullWidth } + fadeOut()
                            )
                        }
                    }
                ) { targetState ->
                    when (targetState) {
                        StudentFormStep.PERSONAL_DETAILS -> PersonalDetailsStep(state, onAction)
                        StudentFormStep.CONTACT_DETAILS -> ContactDetailsStep(state, onAction)
                        StudentFormStep.ACADEMIC_DETAILS -> AcademicDetailsStep(state, onAction)
                    }
                }

                Spacer(Modifier.height(16.dp))

                FormNavigationButtons(state, onAction)
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
                    DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES -> {
                        onConfirmNavigateBack()
                    }
                    DialogIntention.GENERIC -> {
                        onAction(AddEditStudentAction.DismissDialog)
                    }
                }
            },
            onDismiss = {
                onAction(AddEditStudentAction.DismissDialog)
            }
        )
    }

    // Date Picker Dialog
    if (state.isDatePickerVisible) {
        DatePickerDialog(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
private fun PersonalDetailsStep(
    state: AddEditStudentState,
    onAction: (AddEditStudentAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Personal Details",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PresencifyTextField(
            value = state.firstName,
            onValueChange = { onAction(AddEditStudentAction.UpdateFirstName(it)) },
            label = "First Name *",
            supportingText = state.firstNameError,
            isError = state.firstNameError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyTextField(
            value = state.middleName,
            onValueChange = { onAction(AddEditStudentAction.UpdateMiddleName(it)) },
            label = "Middle Name",
            supportingText = state.middleNameError,
            isError = state.middleNameError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyTextField(
            value = state.lastName,
            onValueChange = { onAction(AddEditStudentAction.UpdateLastName(it)) },
            label = "Last Name *",
            supportingText = state.lastNameError,
            isError = state.lastNameError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyDropDownMenuBox<Gender>(
            value = state.gender?.toDisplayLabel() ?: "",
            options = Gender.entries,
            onSelectItem = { onAction(AddEditStudentAction.UpdateGender(it)) },
            label = "Gender *",
            itemToString = { it.toDisplayLabel() },
            expanded = state.isGenderDropdownOpen,
            onDropDownVisibilityChanged = { onAction(AddEditStudentAction.ChangeGenderDropDownVisibility(it)) },
            supportingText = state.genderError,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyTextField(
            value = state.dob?.toReadableString() ?: "",
            onValueChange = {},
            label = "Date of Birth",
            supportingText = state.dobError,
            isError = state.dobError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAction(AddEditStudentAction.ChangeDatePickerVisibility(true)) },
            trailingIcon = {
                IconButton(onClick = { onAction(AddEditStudentAction.ChangeDatePickerVisibility(true)) }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select Date",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
    }
}

@Composable
private fun ContactDetailsStep(
    state: AddEditStudentState,
    onAction: (AddEditStudentAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Details",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PresencifyTextField(
            value = state.email,
            onValueChange = { onAction(AddEditStudentAction.UpdateEmail(it)) },
            label = "Email *",
            supportingText = state.emailError,
            isError = state.emailError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyTextField(
            value = state.phoneNumber,
            onValueChange = { onAction(AddEditStudentAction.UpdatePhoneNumber(it)) },
            label = "Phone Number *",
            supportingText = state.phoneNumberError,
            isError = state.phoneNumberError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyTextField(
            value = state.parentEmail,
            onValueChange = { onAction(AddEditStudentAction.UpdateParentEmail(it)) },
            label = "Parent's Email",
            supportingText = state.parentEmailError,
            isError = state.parentEmailError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AcademicDetailsStep(
    state: AddEditStudentState,
    onAction: (AddEditStudentAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Academic Details",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PresencifyTextField(
            value = state.prn,
            onValueChange = { onAction(AddEditStudentAction.UpdatePrn(it)) },
            label = "PRN *",
            supportingText = state.prnError,
            isError = state.prnError != null,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyDropDownMenuBox<Int>(
            value = state.admissionYear?.toString() ?: "",
            options = state.admissionYearOptions,
            onSelectItem = { onAction(AddEditStudentAction.UpdateAdmissionYear(it)) },
            label = "Admission Year *",
            itemToString = { it.toString() },
            expanded = state.isAdmissionYearDropdownOpen,
            onDropDownVisibilityChanged = { onAction(AddEditStudentAction.ChangeAdmissionYearDropDownVisibility(it)) },
            supportingText = state.admissionYearError,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyDropDownMenuBox<AdmissionType>(
            value = state.admissionType?.toDisplayLabel() ?: "",
            options = AdmissionType.entries,
            onSelectItem = { onAction(AddEditStudentAction.UpdateAdmissionType(it)) },
            label = "Admission Type *",
            itemToString = { it.toDisplayLabel() },
            expanded = state.isAdmissionTypeDropdownOpen,
            onDropDownVisibilityChanged = { onAction(AddEditStudentAction.ChangeAdmissionTypeDropDownVisibility(it)) },
            supportingText = state.admissionTypeError,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyDropDownMenuBox<Branch>(
            value = state.branchOptions.find { it.id == state.selectedBranchId }?.abbreviation ?: "",
            options = state.branchOptions,
            onSelectItem = { onAction(AddEditStudentAction.UpdateBranch(it.id)) },
            label = "Branch *",
            itemToString = { it.abbreviation },
            expanded = state.isBranchDropdownOpen,
            onDropDownVisibilityChanged = { onAction(AddEditStudentAction.ChangeBranchDropDownVisibility(it)) },
            supportingText = state.branchError,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        PresencifyDropDownMenuBox<Scheme>(
            value = state.schemeOptions.find { it.id == state.selectedSchemeId }?.name ?: "",
            options = state.schemeOptions,
            onSelectItem = { onAction(AddEditStudentAction.UpdateScheme(it.id)) },
            label = "Scheme *",
            itemToString = { it.name },
            expanded = state.isSchemeDropdownOpen,
            onDropDownVisibilityChanged = { onAction(AddEditStudentAction.ChangeSchemeDropDownVisibility(it)) },
            supportingText = state.schemeError,
            enabled = !state.isLoading && !state.isSubmitting,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun FormNavigationButtons(
    state: AddEditStudentState,
    onAction: (AddEditStudentAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (state.currentStep != StudentFormStep.PERSONAL_DETAILS) {
            PresencifyOutlinedButton(
                onClick = { onAction(AddEditStudentAction.BackStep) },
                enabled = !state.isSubmitting,
                modifier = Modifier
                    .weight(.5f)
                    .padding(end = 4.dp),
                text = "Back"
            )
        }

        val buttonText = when (state.currentStep) {
            StudentFormStep.ACADEMIC_DETAILS -> if (state.isEditMode) "Update" else "Submit"
            else -> "Next"
        }

        PresencifyButton(
            onClick = { onAction(AddEditStudentAction.ValidateAndNext) },
            text = buttonText,
            isLoading = state.isSubmitting,
            enabled = !state.isSubmitting,
            modifier = Modifier
                .weight(.5f)
                .padding(start = if (state.currentStep != StudentFormStep.PERSONAL_DETAILS) 4.dp else 0.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialog(
    state: AddEditStudentState,
    onAction: (AddEditStudentAction) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.dob?.toEpochDays()?.let { it * 24 * 60 * 60 * 1000L }
    )

    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    DatePickerDialog(
        onDismissRequest = { onAction(AddEditStudentAction.ChangeDatePickerVisibility(false)) },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val epochDays = millis / (24 * 60 * 60 * 1000)
                        val selectedDate = LocalDate.fromEpochDays(epochDays.toInt())
                        onAction(AddEditStudentAction.UpdateDob(selectedDate))
                    }
                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onAction(AddEditStudentAction.ChangeDatePickerVisibility(false)) }
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


