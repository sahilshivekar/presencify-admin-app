package edu.watumull.presencify.feature.users.add_edit_teacher

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.core.presentation.validation.*
import edu.watumull.presencify.feature.users.navigation.UsersRoutes
import kotlinx.coroutines.launch

class AddEditTeacherViewModel(
    private val teacherRepository: TeacherRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<AddEditTeacherState, AddEditTeacherEvent, AddEditTeacherAction>(
    initialState = AddEditTeacherState(
        teacherId = savedStateHandle.toRoute<UsersRoutes.AddEditTeacher>().teacherId,
        isEditMode = savedStateHandle.toRoute<UsersRoutes.AddEditTeacher>().teacherId != null
    )
) {

    init {
        viewModelScope.launch {
            // If in edit mode, load teacher details
            state.teacherId?.let { teacherId ->
                loadTeacherDetails(teacherId)
            }
        }
    }

    private suspend fun loadTeacherDetails(teacherId: String) {
        updateState { it.copy(isLoading = true) }

        teacherRepository.getTeacherById(teacherId)
            .onSuccess { teacher ->
                updateState {
                    it.copy(
                        isLoading = false,
                        firstName = teacher.firstName,
                        middleName = teacher.middleName ?: "",
                        lastName = teacher.lastName,
                        gender = teacher.gender,
                        highestQualification = teacher.highestQualification ?: "",
                        role = teacher.role,
                        email = teacher.email,
                        phoneNumber = teacher.phoneNumber,
                        teacherImageUrl = teacher.teacherImageUrl
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoading = false,
                        dialogState = AddEditTeacherState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Teacher",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: AddEditTeacherAction) {
        when (action) {
            is AddEditTeacherAction.BackButtonClick -> handleBackNavigation()
            is AddEditTeacherAction.DismissDialog -> updateState { it.copy(dialogState = null) }

            // Personal Details
            is AddEditTeacherAction.UpdateFirstName -> {
                val validation = action.firstName.validateAsFirstName()
                updateState {
                    it.copy(firstName = action.firstName, firstNameError = if (validation.successful) null else validation.errorMessage)
                }
            }
            is AddEditTeacherAction.UpdateMiddleName -> {
                val validation = action.middleName.validateAsMiddleName()
                updateState {
                    it.copy(middleName = action.middleName, middleNameError = if (validation.successful) null else validation.errorMessage)
                }
            }
            is AddEditTeacherAction.UpdateLastName -> {
                val validation = action.lastName.validateAsLastName()
                updateState {
                    it.copy(lastName = action.lastName, lastNameError = if (validation.successful) null else validation.errorMessage)
                }
            }
            is AddEditTeacherAction.UpdateGender -> updateState {
                it.copy(gender = action.gender, genderError = null, isGenderDropdownOpen = false)
            }
            is AddEditTeacherAction.UpdateHighestQualification -> updateState {
                it.copy(highestQualification = action.qualification, highestQualificationError = null)
            }
            is AddEditTeacherAction.UpdateRole -> updateState {
                it.copy(role = action.role, roleError = null, isRoleDropdownOpen = false)
            }
            is AddEditTeacherAction.ChangeGenderDropDownVisibility -> updateState {
                it.copy(isGenderDropdownOpen = action.isVisible)
            }
            is AddEditTeacherAction.ChangeRoleDropDownVisibility -> updateState {
                it.copy(isRoleDropdownOpen = action.isVisible)
            }

            // Contact Details
            is AddEditTeacherAction.UpdateEmail -> {
                val validation = action.email.validateAsEmail()
                updateState {
                    it.copy(email = action.email, emailError = if (validation.successful) null else validation.errorMessage)
                }
            }
            is AddEditTeacherAction.UpdatePhoneNumber -> {
                val validation = action.phoneNumber.validateAsPhoneNumber()
                updateState {
                    it.copy(phoneNumber = action.phoneNumber, phoneNumberError = if (validation.successful) null else validation.errorMessage)
                }
            }

            // Image
            is AddEditTeacherAction.UpdateTeacherImage -> updateState {
                it.copy(teacherImageBytes = action.imageBytes)
            }
            is AddEditTeacherAction.ToggleImageDialog -> updateState {
                it.copy(isImageDialogVisible = !it.isImageDialogVisible)
            }

            // Navigation
            is AddEditTeacherAction.SubmitClick -> {
                viewModelScope.launch {
                    submitForm()
                }
            }
        }
    }

    private fun handleBackNavigation() {
        if (hasUnsavedChanges()) {
            updateState {
                it.copy(
                    dialogState = AddEditTeacherState.DialogState(
                        dialogType = DialogType.CONFIRM_NORMAL_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_NAVIGATION_WITH_UNSAVED_CHANGES,
                        title = "Unsaved Changes",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                            "You have unsaved changes. Are you sure you want to leave?"
                        )
                    )
                )
            }
        } else {
            sendEvent(AddEditTeacherEvent.NavigateBack)
        }
    }

    fun confirmNavigateBack() {
        updateState { it.copy(dialogState = null) }
        sendEvent(AddEditTeacherEvent.NavigateBack)
    }

    private fun hasUnsavedChanges(): Boolean {
        return state.firstName.isNotBlank() ||
                state.lastName.isNotBlank() ||
                state.email.isNotBlank() ||
                state.phoneNumber.isNotBlank()
    }

    private fun validateForm(): Boolean {
        val firstNameValidation = state.firstName.validateAsFirstName()
        val firstNameError = if (firstNameValidation.successful) null else firstNameValidation.errorMessage

        val lastNameValidation = state.lastName.validateAsLastName()
        val lastNameError = if (lastNameValidation.successful) null else lastNameValidation.errorMessage

        val genderError = if (state.gender == null) "Gender is required" else null

        val roleError = if (state.role == null) "Role is required" else null

        val emailValidation = state.email.validateAsEmail()
        val emailError = if (emailValidation.successful) null else emailValidation.errorMessage

        val phoneValidation = state.phoneNumber.validateAsPhoneNumber()
        val phoneNumberError = if (phoneValidation.successful) null else phoneValidation.errorMessage

        updateState {
            it.copy(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                genderError = genderError,
                roleError = roleError,
                emailError = emailError,
                phoneNumberError = phoneNumberError
            )
        }

        return firstNameError == null && lastNameError == null && genderError == null &&
                roleError == null && emailError == null && phoneNumberError == null
    }

    private suspend fun submitForm() {
        if (!validateForm()) return

        updateState { it.copy(isSubmitting = true) }

        val result = if (state.isEditMode && state.teacherId != null) {
            updateTeacher()
        } else {
            addTeacher()
        }
        result
            .onSuccess {
                updateState { it.copy(isSubmitting = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = if (state.isEditMode) "Teacher updated successfully" else "Teacher added successfully"
                        )
                    )
                }
                sendEvent(AddEditTeacherEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isSubmitting = false,
                        dialogState = AddEditTeacherState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = if (state.isEditMode) "Error Updating Teacher" else "Error Adding Teacher",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private suspend fun addTeacher() = teacherRepository.addTeacher(
        firstName = state.firstName,
        middleName = state.middleName.ifBlank { null },
        lastName = state.lastName,
        email = state.email,
        phoneNumber = state.phoneNumber,
        gender = state.gender!!,
        highestQualification = state.highestQualification.ifBlank { null },
        role = state.role!!,
        isActive = true,
        teacherImage = state.teacherImageBytes
    )

    private suspend fun updateTeacher() = teacherRepository.updateTeacherDetails(
        id = state.teacherId!!,
        firstName = state.firstName,
        middleName = state.middleName.ifBlank { null },
        lastName = state.lastName,
        email = state.email,
        role = state.role,
        gender = state.gender,
        highestQualification = state.highestQualification.ifBlank { null },
        phoneNumber = state.phoneNumber,
    )
}

