package edu.watumull.presencify.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import edu.watumull.presencify.core.design.systems.components.PresencifyListItem

/**
 * List item component for displaying Branch information.
 *
 * @param name The name of the branch.
 * @param abbreviation The abbreviation of the branch.
 * @param feedback Optional feedback message to display.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun BranchListItem(
    name: String,
    abbreviation: String,
    feedback: ListItemFeedback? = null,
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
            Column {
                Text(
                    text = "Abbreviation: $abbreviation",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                feedback?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(4.dp))
                    AnimatedVisibility(
                        visible = true,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        val (color, message) = when (it) {
                            is ListItemFeedback.Success -> Color.Green to it.message
                            is ListItemFeedback.Error -> MaterialTheme.colorScheme.error to it.message
                        }
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodySmall,
                            color = color
                        )
                    }
                }
            }
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
