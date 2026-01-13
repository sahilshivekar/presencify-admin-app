package edu.watumull.presencify.core.design.systems.components.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * A loading dialog with rounded square borders containing a circular progress indicator.
 * Prevents user interactions while showing loading state.
 *
 * @param modifier Modifier to be applied to the dialog
 * @param message Optional loading message to display
 * @param isVisible Whether the dialog is visible
 */
@Composable
fun PresencifyLoadingDialog(
    modifier: Modifier = Modifier,
    message: String? = null,
    isVisible: Boolean,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = {}, // Non-dismissible to prevent accidental actions
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Card(
                modifier = modifier
                    .semantics { testTag = "PresencifyLoadingDialog" }
                    .testTag("PresencifyLoadingDialog"),
                shape = RoundedCornerShape(24.dp), // Rounded square borders
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Circular progress indicator inside the square dialog
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )

                    message?.let {
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}