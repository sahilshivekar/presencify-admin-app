package edu.watumull.presencify.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import edu.watumull.presencify.core.design.systems.components.PresencifyListItem

/**
 * List item component for displaying Branch information.
 *
 * @param name The name of the branch.
 * @param abbreviation The abbreviation of the branch.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun BranchListItem(
    name: String,
    abbreviation: String,
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
        onClick = onClick,
        modifier = modifier
    )
}


@Composable
fun BranchListItemPreview() {
    MaterialTheme {
        BranchListItem(
            name = "Computer Science",
            abbreviation = "CS",
            onClick = {}
        )
    }
}

@Composable
fun BranchListItemLongNamePreview() {
    MaterialTheme {
        BranchListItem(
            name = "Electronics and Telecommunication Engineering",
            abbreviation = "EXTC"
        )
    }
}

