package edu.watumull.presencify.feature.admin.auth.login

import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.admin_auth.AdminAuthRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import kotlinx.coroutines.launch

class AdminLoginViewModel(
    private val adminAuthRepository: AdminAuthRepository,
) : BaseViewModel<AdminLoginState, AdminLoginEvent, AdminLoginAction>(
    initialState = AdminLoginState()
) {

    override fun handleAction(action: AdminLoginAction) {
        when (action) {
            is AdminLoginAction.ChangeEmailOrUsername -> {
                updateState {
                    it.copy(
                        emailOrUsername = action.emailOrUsername,
                        emailOrUsernameError = null
                    )
                }
            }

            is AdminLoginAction.ChangePassword -> {
                updateState {
                    it.copy(
                        password = action.password,
                        passwordError = null
                    )
                }
            }

            is AdminLoginAction.TogglePasswordVisibility -> {
                updateState { it.copy(isPasswordVisible = action.isVisible) }
            }

            is AdminLoginAction.ClickLogin -> {
                performLogin()
            }

            is AdminLoginAction.ClickBackButton -> {
                sendEvent(AdminLoginEvent.NavigateBack)
            }

            is AdminLoginAction.ClickForgotPassword -> {
                sendEvent(AdminLoginEvent.NavigateToForgotPassword)
            }

            is AdminLoginAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }
        }
    }

    private fun performLogin() {
        val currentState = state
        val emailOrUsername = currentState.emailOrUsername.trim()
        val password = currentState.password

        // Reset errors
        updateState {
            it.copy(
                emailOrUsernameError = null,
                passwordError = null
            )
        }

        // Validation
        var hasErrors = false
        if (emailOrUsername.isBlank()) {
            updateState { it.copy(emailOrUsernameError = "Email or username is required") }
            hasErrors = true
        }

        if (password.isBlank()) {
            updateState { it.copy(passwordError = "Password cannot be empty") }
            hasErrors = true
        }

        if (hasErrors) {
            return
        }

        // Start loading
        updateState {
            it.copy(
                isLoading = true,
                isPasswordVisible = false // Hide password during login
            )
        }

        viewModelScope.launch {
            adminAuthRepository.login(emailOrUsername, password)
                .onSuccess {
                    updateState { it.copy(isLoading = false) }

                    SnackbarController.sendEvent(SnackbarEvent("Login successful"))

                    sendEvent(AdminLoginEvent.NavigateToHome)
                }
                .onError { error ->

                    updateState {
                        it.copy(
                            isLoading = false,
                            dialogState = AdminLoginState.DialogState(
                                isVisible = true,
                                title = "Login Failed",
                                message = error.toUiText(),
                                dialogType = DialogType.ERROR,
                                dialogIntention = DialogIntention.LOGIN_ERROR
                            )
                        )
                    }
                }
        }
    }
}
