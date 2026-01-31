package edu.watumull.presencify.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem
import edu.watumull.presencify.core.domain.enums.RoomType

/**
 * List item component for displaying Room information.
 *
 * @param roomNumber The room number.
 * @param sittingCapacity The sitting capacity of the room.
 * @param type Optional room type (Classroom, Lab, Office).
 * @param name Optional name of the room.
 * @param feedback Optional feedback message to display.
 * @param trailingIcon Optional trailing icon composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun RoomListItem(
    roomNumber: String,
    sittingCapacity: Int,
    type: RoomType? = null,
    name: String? = null,
    feedback: ListItemFeedback? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text(
                        text = roomNumber,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                if (type != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = type.toDisplayLabel(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        supportingContent = {
            Column {
                name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "Sitting capacity: $sittingCapacity",
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
        trailingContent = trailingIcon,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun RoomListItemPreview() {
    MaterialTheme {
        RoomListItem(
            roomNumber = "101",
            type = RoomType.CLASSROOM,
            name = "Computer Lab A",
            sittingCapacity = 60,
            onClick = {}
        )
    }
}

@Composable
fun RoomListItemMinimalPreview() {
    MaterialTheme {
        RoomListItem(
            roomNumber = "205",
            sittingCapacity = 40
        )
    }
}

@Composable
fun RoomListItemLabPreview() {
    MaterialTheme {
        RoomListItem(
            roomNumber = "B-302",
            type = RoomType.LAB,
            name = "Physics Laboratory",
            sittingCapacity = 30
        )
    }
}
