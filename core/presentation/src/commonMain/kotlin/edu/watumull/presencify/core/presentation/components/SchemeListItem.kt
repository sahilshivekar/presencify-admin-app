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
 * List item component for displaying Scheme information.
 *
 * @param name The name of the scheme.
 * @param universityName The university name associated with the scheme.
 * @param feedback Optional feedback message to display.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun SchemeListItem(
    name: String,
    universityName: String,
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
                    text = "University: $universityName",
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
