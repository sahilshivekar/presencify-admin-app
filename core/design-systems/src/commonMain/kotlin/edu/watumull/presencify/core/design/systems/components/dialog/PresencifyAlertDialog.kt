package edu.watumull.presencify.core.design.systems.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import edu.watumull.presencify.core.design.systems.components.PresencifyTextButton

/**
 * A basic alert dialog with confirm and dismiss buttons.
 * Uses [DialogType] to control styling and behavior.
 *
 * @param modifier Modifier to be applied to the dialog
 * @param title Optional title text
 * @param onDismiss Callback when user dismisses the dialog
 * @param onConfirm Callback when user confirms the action
 * @param isVisible Whether the dialog is visible
 * @param dialogType The type of dialog that affects styling and button layout
 */
@Composable
fun PresencifyAlertDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    dialogType: DialogType,
    title: String? = null,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null,
) {
    val containerColor = when (dialogType) {
        DialogType.ERROR -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    val buttonColors = when (dialogType) {
        DialogType.CONFIRM_RISKY_ACTION -> ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            containerColor = Color.Transparent
        )

        DialogType.ERROR -> ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            containerColor = Color.Transparent
        )

        else -> ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
    }

    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                if (dialogType == DialogType.CONFIRM_RISKY_ACTION || dialogType == DialogType.CONFIRM_NORMAL_ACTION) {
                    PresencifyTextButton(
                        content = { Text("Confirm") },
                        onClick = onConfirm ?: {},
                        colors = buttonColors,
                        modifier = Modifier.testTag("AcceptAlertButton"),
                    )
                } else Unit
            },
            dismissButton = {
                PresencifyTextButton(
                    content = {
                        Text(
                            text = when (dialogType) {
                                DialogType.INFO -> "Ok"
                                DialogType.CONFIRM_RISKY_ACTION -> "Cancel"
                                DialogType.CONFIRM_NORMAL_ACTION -> "Cancel"
                                DialogType.ERROR -> "Ok"
                                DialogType.SUCCESS -> "Ok"
                            }
                        )
                    },
                    onClick = onDismiss,
                    colors = buttonColors,
                    modifier = Modifier.testTag("DismissAlertButton"),
                )
            },
            title = title?.let {
                {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.testTag("AlertTitleText"),
                    )
                }
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("AlertContentText"),
                )
            },
            containerColor = containerColor,
            modifier = modifier.semantics {
                testTag = "AlertPopup"
            },
        )
    }
}