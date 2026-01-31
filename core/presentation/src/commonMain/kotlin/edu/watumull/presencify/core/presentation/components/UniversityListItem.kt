package edu.watumull.presencify.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem

/**
 * List item component for displaying University information.
 *
 * @param name The name of the university.
 * @param abbreviation The abbreviation of the university.
 * @param trailingContent Optional trailing content composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun UniversityListItem(
    name: String,
    abbreviation: String,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        headlineContent = {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        supportingContent = {
            Text(
                text = "Abbreviation: $abbreviation",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = trailingContent,
        onClick = onClick,
        modifier = modifier
    )
}
