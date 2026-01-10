package edu.watumull.presencify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import edu.watumull.presencify.core.data.repository.auth.RoleRepository
import edu.watumull.presencify.core.design.systems.theme.PresencifyTheme
import edu.watumull.presencify.core.domain.model.auth.UserRole
import edu.watumull.presencify.core.presentation.global_snackbar.ObserveAsEvents
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

// Define the CompositionLocal for UserRole
val LocalUserRole = compositionLocalOf<UserRole?> { null }

@Composable
@Preview
fun App() {
    val roleRepository = koinInject<RoleRepository>()
    val userRole by roleRepository.getUserRole().collectAsState(initial = null)

    CompositionLocalProvider(LocalUserRole provides userRole) {

        PresencifyTheme {

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            ObserveAsEvents(
                SnackbarController.events,
            ) { event ->
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()

                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action?.name,
                        duration = if (event.action == null) SnackbarDuration.Short else SnackbarDuration.Long
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        event.action?.action()
                    }
                }
            }

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = { }) {
                    Text("Click me!")
                }
            }
        }
    }
}