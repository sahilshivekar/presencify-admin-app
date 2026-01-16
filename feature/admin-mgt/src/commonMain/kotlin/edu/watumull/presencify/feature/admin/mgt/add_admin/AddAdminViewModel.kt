package edu.watumull.presencify.feature.admin.mgt.add_admin

import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.admin.AdminRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.core.presentation.validation.validateAsAdminUsername
import edu.watumull.presencify.core.presentation.validation.validateAsEmail
import edu.watumull.presencify.core.presentation.validation.validateAsPassword
import kotlinx.coroutines.launch

class AddAdminViewModel(
    private val adminRepository: AdminRepository,
) : BaseViewModel<AddAdminState, AddAdminEvent, AddAdminAction>(
    initialState = AddAdminState()
) {

    override fun handleAction(action: AddAdminAction) {
        when (action) {
            is AddAdminAction.ChangeEmail -> {
                updateState {
                    it.copy(
                        email = action.email,
                        emailError = null
                    )
                }

                // Validate on keystroke for forms
                val validationResult = action.email.validateAsEmail()
                updateState {
                    it.copy(
                        emailError = if (validationResult.successful) null else validationResult.errorMessage
                    )
                }
            }

            is AddAdminAction.ChangeUsername -> {
                updateState {
                    it.copy(
                        username = action.username,
                        usernameError = null
                    )
                }

                // Validate on keystroke for forms
                val validationResult = action.username.validateAsAdminUsername()
                updateState {
                    it.copy(
                        usernameError = if (validationResult.successful) null else validationResult.errorMessage
                    )
                }
            }

            is AddAdminAction.ChangePassword -> {
                updateState {
                    it.copy(
                        password = action.password,
                        passwordError = null,
                        confirmPasswordError = null
                    )
                }

                // Validate on keystroke for forms
                val validationResult = action.password.validateAsPassword()
                updateState {
                    it.copy(
                        passwordError = if (validationResult.successful) null else validationResult.errorMessage
                    )
                }

                // Also check confirm password match if it's not empty
                if (state.confirmPassword.isNotEmpty()) {
                    validatePasswordMatch(action.password, state.confirmPassword)
                }
            }

            is AddAdminAction.ChangeConfirmPassword -> {
                updateState {
                    it.copy(
                        confirmPassword = action.confirmPassword,
                        confirmPasswordError = null
                    )
                }

                // Validate password match
                validatePasswordMatch(state.password, action.confirmPassword)
            }

            is AddAdminAction.TogglePasswordVisibility -> {
                updateState {
                    it.copy(isPasswordVisible = !it.isPasswordVisible)
                }
            }

            is AddAdminAction.ClickAddAdmin -> {
                addAdmin()
            }

            is AddAdminAction.ClickBackButton -> {
                sendEvent(AddAdminEvent.NavigateBack)
            }

            is AddAdminAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }
        }
    }

    private fun validatePasswordMatch(password: String, confirmPassword: String) {
        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            updateState {
                it.copy(confirmPasswordError = "Password and confirm password are not the same")
            }
        } else {
            updateState {
                it.copy(confirmPasswordError = null)
            }
        }
    }

    private fun addAdmin() {
        val currentState = state
        val email = currentState.email.trim()
        val username = currentState.username.trim()
        val password = currentState.password
        val confirmPassword = currentState.confirmPassword

        // Final validation on submit
        val emailValidationResult = email.validateAsEmail()
        val usernameValidationResult = username.validateAsAdminUsername()
        val passwordValidationResult = password.validateAsPassword()

        updateState {
            it.copy(
                emailError = if (emailValidationResult.successful) null else emailValidationResult.errorMessage,
                usernameError = if (usernameValidationResult.successful) null else usernameValidationResult.errorMessage,
                passwordError = if (passwordValidationResult.successful) null else passwordValidationResult.errorMessage,
                confirmPasswordError = if (password == confirmPassword) null else "Password and confirm password are not the same"
            )
        }

        val hasError = !emailValidationResult.successful ||
                      !usernameValidationResult.successful ||
                      !passwordValidationResult.successful ||
                      password != confirmPassword

        if (hasError) {
            updateState { it.copy(isPasswordVisible = true) }
            return
        }

        updateState {
            it.copy(
                isAdding = true,
                emailError = null,
                usernameError = null,
                passwordError = null,
                confirmPasswordError = null
            )
        }

        viewModelScope.launch {
            adminRepository.addAdmin(email, username, password)
                .onSuccess { admin ->
                    updateState { it.copy(isAdding = false) }

                    SnackbarController.sendEvent(SnackbarEvent("Admin added successfully."))

                    sendEvent(AddAdminEvent.NavigateToAdminDetails)
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            isAdding = false,
                            isPasswordVisible = true,
                            dialogState = AddAdminState.DialogState(
                                isVisible = true,
                                title = "Failed to Add Admin",
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
