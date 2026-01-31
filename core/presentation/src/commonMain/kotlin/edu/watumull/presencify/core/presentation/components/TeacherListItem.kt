package edu.watumull.presencify.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import edu.watumull.presencify.core.domain.enums.TeacherRole

/**
 * List item component for displaying Teacher information with profile image.
 *
 * @param teacherName The name of the teacher (required).
 * @param role Optional teacher role (Teacher, Head of Department, Principal).
 * @param teacherImageUrl Optional URL for teacher profile image.
 * @param feedback Optional feedback message to display.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun TeacherListItem(
    teacherName: String,
    role: TeacherRole? = null,
    teacherImageUrl: String? = null,
    feedback: ListItemFeedback? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        leadingContent = {
            if (teacherImageUrl != null) {
                AsyncImage(
                    model = teacherImageUrl,
                    contentDescription = "Teacher profile image",
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
                text = teacherName,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        supportingContent = {
            Column {
                role?.let {
                    Text(
                        text = role.value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
