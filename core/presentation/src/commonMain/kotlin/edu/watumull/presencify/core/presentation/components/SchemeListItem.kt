package edu.watumull.presencify.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem

/**
 * List item component for displaying Scheme information.
 *
 * @param name The name of the scheme.
 * @param universityName The university name associated with the scheme.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun SchemeListItem(
    name: String,
    universityName: String,
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
                text = "University: $universityName",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun SchemeListItemPreview() {
    MaterialTheme {
        SchemeListItem(
            name = "2023 Scheme",
            universityName = "University of Mumbai",
            onClick = {}
        )
    }
}

@Composable
fun SchemeListItemLongNamePreview() {
    MaterialTheme {
        SchemeListItem(
            name = "Revised Engineering Curriculum 2023-24",
            universityName = "Maharashtra State Board of Technical Education"
        )
    }
}

