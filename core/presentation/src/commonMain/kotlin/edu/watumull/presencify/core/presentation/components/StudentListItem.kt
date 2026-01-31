package edu.watumull.presencify.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem

/**
 * List item component for displaying Student information with profile image.
 *
 * @param studentName The name of the student (required).
 * @param studentBranch The branch/department of the student (required).
 * @param studentYear Optional year (e.g., "FE", "SE", "TE", "BE").
 * @param studentImageUrl Optional URL for student profile image.
 * @param feedback Optional feedback message to display.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun StudentListItem(
    studentName: String,
    studentBranch: String,
    studentYear: String? = null,
    studentImageUrl: String? = null,
    feedback: ListItemFeedback? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        leadingContent = {
            if (studentImageUrl != null) {
                AsyncImage(
                    model = studentImageUrl,
                    contentDescription = "Student profile image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Default profile",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        headlineContent = {
            Text(
                text = studentName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        supportingContent = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = studentBranch,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (studentYear != null) {
                        Text(
                            text = " • ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = studentYear,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
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
        trailingContent = trailingContent,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun StudentListItemPreview() {
    MaterialTheme {
        StudentListItem(
            studentName = "Shivam Pandey",
            studentBranch = "Comp. Engg.",
            studentYear = "TE",
            studentImageUrl = "https://picsum.photos/200",
            onClick = {}
        )
    }
}

@Composable
fun StudentListItemWithYearPreview() {
    MaterialTheme {
        StudentListItem(
            studentName = "Priya Sharma",
            studentBranch = "Electronics Engg.",
            studentYear = "BE",
            studentImageUrl = "https://picsum.photos/seed/priya/200",
            onClick = {}
        )
    }
}

@Composable
fun StudentListItemWithoutYearPreview() {
    MaterialTheme {
        StudentListItem(
            studentName = "Rahul Verma",
            studentBranch = "Mechanical Engg.",
            studentImageUrl = null,
            onClick = {}
        )
    }
}
