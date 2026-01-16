package edu.watumull.presencify.feature.admin.mgt.admin_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.admin.AdminRepository
import edu.watumull.presencify.core.domain.repository.admin_auth.AdminAuthRepository
import edu.watumull.presencify.core.presentation.UiText
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.core.presentation.validation.validateAsAdminUsername
import edu.watumull.presencify.core.presentation.validation.validateAsEmail
import kotlinx.coroutines.launch

class AdminDetailsViewModel(
    private val adminRepository: AdminRepository,
    private val adminAuthRepository: AdminAuthRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AdminDetailsState, AdminDetailsEvent, AdminDetailsAction>(
    initialState = AdminDetailsState()
) {

    init {
        loadAdminDetails()
    }

    override fun handleAction(action: AdminDetailsAction) {
        when (action) {
            is AdminDetailsAction.ChangeEmail -> {
                updateState {
                    it.copy(
                        editableEmail = action.email,
                        emailError = null
                    )
                }
            }

            is AdminDetailsAction.ChangeUsername -> {
                updateState {
                    it.copy(
                        editableUsername = action.username,
                        usernameError = null
                    )
                }
            }

            is AdminDetailsAction.ClickEditDetails -> {
                updateState {
                    it.copy(
                        isEditingDetails = true,
                        isUsernameEmailEnabled = true
                    )
                }
            }

            is AdminDetailsAction.ClickCancelEditingDetails -> {
                updateState {
                    it.copy(
                        isEditingDetails = false,
                        isUsernameEmailEnabled = false,
                        editableEmail = state.orgEmail,
                        editableUsername = state.orgUsername,
                        emailError = null,
                        usernameError = null
                    )
                }
            }

            is AdminDetailsAction.ClickUpdateDetails -> {
                updateAdminDetails()
            }

            is AdminDetailsAction.ClickVerifyEmail -> {
                sendEmailVerificationCode()
            }

            is AdminDetailsAction.ClickLogout -> {
                logoutAdmin()
            }

            is AdminDetailsAction.ClickRemoveAccount -> {
                showRemoveAccountDialog()
            }

            is AdminDetailsAction.ConfirmRemoveAccount -> {
                removeAdmin()
            }

            is AdminDetailsAction.ClickUpdatePassword -> {
                sendEvent(AdminDetailsEvent.NavigateToUpdatePassword)
            }

            is AdminDetailsAction.ClickAddAdmin -> {
                sendEvent(AdminDetailsEvent.NavigateToAddAdmin)
            }

            is AdminDetailsAction.ClickBackButton -> {
                sendEvent(AdminDetailsEvent.NavigateBack)
            }

            is AdminDetailsAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }
        }
    }

    private fun loadAdminDetails() {
        updateState { it.copy(viewState = AdminDetailsState.ViewState.Loading) }

        viewModelScope.launch {
            adminRepository.getAdminDetails()
                .onSuccess { admin ->
                    updateState {
                        it.copy(
                            viewState = AdminDetailsState.ViewState.Content,
                            orgEmail = admin.email,
                            orgUsername = admin.username,
                            editableEmail = admin.email,
                            editableUsername = admin.username,
                            isVerified = admin.isVerified
                        )
                    }
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            viewState = AdminDetailsState.ViewState.Error(error.toUiText())
                        )
                    }
                }
        }
    }

    private fun updateAdminDetails() {
        val currentState = state
        val email = currentState.editableEmail.trim()
        val username = currentState.editableUsername.trim()

        // Validation
        val emailValidationResult = email.validateAsEmail()
        val usernameValidationResult = username.validateAsAdminUsername()

        updateState {
            it.copy(
                emailError = if (emailValidationResult.successful) null else emailValidationResult.errorMessage,
                usernameError = if (usernameValidationResult.successful) null else usernameValidationResult.errorMessage
            )
        }

        if (!emailValidationResult.successful || !usernameValidationResult.successful) {
            return
        }

        // Check if any changes were made
        if (email == currentState.orgEmail && username == currentState.orgUsername) {
            viewModelScope.launch {
                SnackbarController.sendEvent(SnackbarEvent("No changes made to the email and username."))
            }
            return
        }

        updateState {
            it.copy(
                isUpdatingDetails = true,
                isUsernameEmailEnabled = false,
                emailError = null,
                usernameError = null
            )
        }

        viewModelScope.launch {
            adminRepository.updateAdminDetails(email, username)
                .onSuccess { admin ->
                    updateState {
                        it.copy(
                            isUpdatingDetails = false,
                            isEditingDetails = false,
                            orgEmail = admin.email,
                            orgUsername = admin.username,
                            editableEmail = admin.email,
                            editableUsername = admin.username,
                            isUsernameEmailEnabled = false,
                            isVerified = admin.isVerified
                        )
                    }

                    SnackbarController.sendEvent(SnackbarEvent("Details updated successfully."))
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            isUpdatingDetails = false,
                            isEditingDetails = true,
                            isUsernameEmailEnabled = true,
                            dialogState = AdminDetailsState.DialogState(
                                isVisible = true,
                                title = "Update Failed",
                                message = error.toUiText(),
                                dialogType = DialogType.ERROR,
                                dialogIntention = DialogIntention.ERROR
                            )
                        )
                    }
                }
        }
    }

    private fun sendEmailVerificationCode() {
        updateState { it.copy(isSendingVerificationCode = true) }

        viewModelScope.launch {
            adminAuthRepository.sendVerificationCodeToEmail()
                .onSuccess {
                    updateState { it.copy(isSendingVerificationCode = false) }

                    SnackbarController.sendEvent(SnackbarEvent("Verification code sent successfully."))

                    sendEvent(AdminDetailsEvent.NavigateToVerifyCode(state.orgEmail))
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            isSendingVerificationCode = false,
                            dialogState = AdminDetailsState.DialogState(
                                isVisible = true,
                                title = "Failed to Send Verification Code",
                                message = error.toUiText(),
                                dialogType = DialogType.ERROR,
                                dialogIntention = DialogIntention.ERROR
                            )
                        )
                    }
                }
        }
    }

    private fun logoutAdmin() {
        updateState { it.copy(isLoggingOut = true) }

        viewModelScope.launch {
            adminAuthRepository.logout()
                .onSuccess {
                    updateState { it.copy(isLoggingOut = false) }

                    SnackbarController.sendEvent(SnackbarEvent("Logged out successfully."))

                    sendEvent(AdminDetailsEvent.NavigateToSelectRole)
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            isLoggingOut = false,
                            dialogState = AdminDetailsState.DialogState(
                                isVisible = true,
                                title = "Logout Failed",
                                message = error.toUiText(),
                                dialogType = DialogType.ERROR,
                                dialogIntention = DialogIntention.ERROR
                            )
                        )
                    }
                }
        }
    }

    private fun showRemoveAccountDialog() {
        updateState {
            it.copy(
                dialogState = AdminDetailsState.DialogState(
                    isVisible = true,
                    dialogType = DialogType.CONFIRM_RISKY_ACTION,
                    dialogIntention = DialogIntention.REMOVE_ACCOUNT_CONFIRMATION,
                    title = "Remove Account",
                    message = UiText.DynamicString("Are you sure you want to remove your account? This action cannot be undone.")
                )
            )
        }
    }

    private fun removeAdmin() {
        updateState {
            it.copy(
                isRemovingAccount = true,
                dialogState = null // Dismiss confirmation dialog
            )
        }

        viewModelScope.launch {
            adminRepository.removeAdmin()
                .onSuccess {
                    updateState { it.copy(isRemovingAccount = false) }

                    SnackbarController.sendEvent(SnackbarEvent("Account removed successfully."))

                    sendEvent(AdminDetailsEvent.NavigateToSelectRole)
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            isRemovingAccount = false,
                            dialogState = AdminDetailsState.DialogState(
                                isVisible = true,
                                title = "Failed to Remove Account",
                                message = error.toUiText(),
                                dialogType = DialogType.ERROR,
                                dialogIntention = DialogIntention.ERROR
                            )
                        )
                    }
                }
        }
    }
}
