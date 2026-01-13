package edu.watumull.presencify.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
 * @param department The department of the teacher (required).
 * @param role Optional teacher role (Teacher, Head of Department, Principal).
 * @param teacherImageUrl Optional URL for teacher profile image.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun TeacherListItem(
    teacherName: String,
    department: String,
    role: TeacherRole? = null,
    teacherImageUrl: String? = null,
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = department,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                role?.let {
                    Text(
                        text = " • ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = it.value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        trailingContent = trailingContent,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun TeacherListItemPreview() {
    MaterialTheme {
        TeacherListItem(
            teacherName = "Dr. Rajesh Kumar",
            department = "Computer Engineering",
            role = TeacherRole.TEACHER,
            teacherImageUrl = "https://picsum.photos/seed/rajesh/200",
            onClick = {}
        )
    }
}

@Composable
fun TeacherListItemWithDesignationPreview() {
    MaterialTheme {
        TeacherListItem(
            teacherName = "Prof. Anjali Mehta",
            department = "Electronics Engineering",
            role = TeacherRole.HEAD_OF_DEPARTMENT,
            teacherImageUrl = "https://picsum.photos/seed/anjali/200",
            onClick = {}
        )
    }
}

@Composable
fun TeacherListItemWithoutDesignationPreview() {
    MaterialTheme {
        TeacherListItem(
            teacherName = "Dr. Suresh Patil",
            department = "Mechanical Engineering",
            role = null,
            teacherImageUrl = null,
            onClick = {}
        )
    }
}

