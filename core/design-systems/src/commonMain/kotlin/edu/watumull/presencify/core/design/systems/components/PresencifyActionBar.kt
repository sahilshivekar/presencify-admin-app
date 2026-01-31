package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A reusable action bar component with leading icon, text, and trailing icon.
 * Similar to dashboard items but more flexible for various use cases.
 *
 * @param text The text to display
 * @param onClick Action to perform when clicked
 * @param modifier Modifier for the component
 * @param leadingIcon Optional leading icon (DrawableResource)
 * @param leadingImageVector Optional leading icon (ImageVector) - takes precedence over leadingIcon
 * @param trailingIcon Optional trailing icon (ImageVector) - defaults to ChevronRight
 * @param leadingIconTint Tint color for the leading icon - defaults to primary color
 * @param trailingIconTint Tint color for the trailing icon - defaults to outline color
 */
@Composable
fun PresencifyActionBar(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: DrawableResource? = null,
    leadingImageVector: ImageVector? = null,
    trailingIcon: ImageVector = Icons.Default.ChevronRight,
    leadingIconTint: Color = MaterialTheme.colorScheme.primary,
    trailingIconTint: Color = MaterialTheme.colorScheme.outline
) {
    PresencifyListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        leadingContent = if (leadingImageVector != null || leadingIcon != null) {
            {
                when {
                    leadingImageVector != null -> Icon(
                        imageVector = leadingImageVector,
                        contentDescription = null,
                        tint = leadingIconTint,
                        modifier = Modifier.size(24.dp)
                    )
                    leadingIcon != null -> Icon(
                        painter = painterResource(leadingIcon),
                        contentDescription = null,
                        tint = leadingIconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        } else null,
        trailingContent = {
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = trailingIconTint
            )
        },
        onClick = onClick
    )
}
