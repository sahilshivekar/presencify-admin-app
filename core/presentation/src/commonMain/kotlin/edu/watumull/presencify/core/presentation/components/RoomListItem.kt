package edu.watumull.presencify.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

