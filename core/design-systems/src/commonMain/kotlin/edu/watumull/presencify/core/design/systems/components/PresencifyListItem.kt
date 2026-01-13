package edu.watumull.presencify.core.design.systems.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Reusable list item component that provides consistent theming and structure across the app.
 *
 * Designed for use in search screens and any list-based UI where consistent formatting is needed.
 * Uses Material 3's ListItem with subtle elevation for professional appearance.
 *
 * All content slots accept composables for maximum flexibility - use Text, Icon, Image, or any custom composable.
 *
 * @param headlineContent Required main content displayed prominently (typically Text).
 * @param modifier Modifier for the list item.
 * @param supportingContent Optional secondary content displayed below the headline (typically Text for subtitle).
 * @param leadingContent Optional content displayed at the start of the item (typically Icon or Avatar).
 * @param trailingContent Optional content displayed at the end of the item (e.g., badge, icon, status indicator).
 * @param onClick Optional click handler. When provided, the item becomes clickable with ripple effect.
 */
@Composable
fun PresencifyListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        headlineContent = headlineContent,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        tonalElevation = 2.dp
    )
}

