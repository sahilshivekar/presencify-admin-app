package edu.watumull.presencify.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem

/**
 * List item component for displaying Course information.
 *
 * @param name The name of the course.
 * @param code The course code.
 * @param schemeName The scheme name associated with the course.
 * @param optionalCourse Optional subject name if the course is optional.
 * @param trailingContent Optional trailing content composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun CourseListItem(
    name: String,
    code: String,
    schemeName: String,
    optionalCourse: String? = null,
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
            Column {
                Text(
                    text = "Subject code: $code",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Scheme: $schemeName",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                optionalCourse?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Badge(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ) {
                        Text(
                            text = "Optional: $it",
                            style = MaterialTheme.typography.labelSmall
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
fun CourseListItemPreview() {
    MaterialTheme {
        CourseListItem(
            name = "Data Structures and Algorithms",
            code = "CS301",
            schemeName = "2023 Scheme",
            onClick = {}
        )
    }
}

@Composable
fun CourseListItemOptionalPreview() {
    MaterialTheme {
        CourseListItem(
            name = "Machine Learning",
            code = "CS405",
            schemeName = "2023 Scheme",
            optionalCourse = "Artificial Intelligence Track"
        )
    }
}

@Composable
fun CourseListItemLongNamePreview() {
    MaterialTheme {
        CourseListItem(
            name = "Advanced Topics in Computer Networks and Security",
            code = "CS501",
            schemeName = "Revised Engineering Curriculum 2023-24",
            optionalCourse = "Cybersecurity Specialization",
            onClick = {}
        )
    }
}

