package edu.watumull.presencify.core.design.systems.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A filter chip component based on Material 3 FilterChip, enhanced with the functionality
 * from your old PresencifyFilterOption component.
 *
 * Features:
 * - Generic type support for options
 * - Separate onSelect/onUnselect callbacks for better control
 * - Conditional icons (Check when selected, Close when unselected)
 * - Material 3 styling with proper colors and elevation
 * - Consistent with your design system
 */
@Composable
fun <T> PresencifyFilterChip(
    modifier: Modifier = Modifier,
    option: T,
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    onUnselect: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    FilterChip(
        selected = selected,
        onClick = {
            if (selected) onUnselect() else onSelect()
        },
        label = {
            androidx.compose.material3.Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = modifier,
        leadingIcon = leadingIcon ?: {
            Icon(
                imageVector = if (selected) Icons.Default.Check else Icons.Default.Close,
                contentDescription = null
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = Color.Transparent,
            labelColor = MaterialTheme.colorScheme.onSurface,
            iconColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            borderColor = MaterialTheme.colorScheme.outline
        )
    )
}