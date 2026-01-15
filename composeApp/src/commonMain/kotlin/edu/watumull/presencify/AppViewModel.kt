package edu.watumull.presencify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.data.repository.auth.RoleRepository
import edu.watumull.presencify.core.domain.model.auth.UserRole
import edu.watumull.presencify.core.presentation.navigation.NavRoute
import edu.watumull.presencify.feature.onboarding.navigation.OnboardingRoutes
import edu.watumull.presencify.navigation.home.Home
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val roleRepository: RoleRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
            val userRole = roleRepository.getUserRole().first()
            _state.update { it.copy(
                userRole = userRole
            )}
            userRole?.let {
                updateDestination(Home)
            } ?: updateDestination(OnboardingRoutes.SelectRole)
        }
    }

    private fun updateDestination(destination: NavRoute) {
        _state.update { it.copy(
            startDestination = destination
        )}
    }
}

data class AppState(
    val userRole: UserRole? = null,
    val startDestination: NavRoute? = null
)