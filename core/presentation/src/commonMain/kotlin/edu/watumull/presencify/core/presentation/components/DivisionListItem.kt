package edu.watumull.presencify.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * List item component for displaying Division information.
 *
 * @param divisionCode The division code.
 * @param batchCodes List of batch codes in this division.
 * @param semesterNumber The semester number.
 * @param semesterAcademicStartYear The academic start year of the semester.
 * @param semesterAcademicEndYear The academic end year of the semester.
 * @param branchAbbreviation The branch abbreviation.
 * @param feedback Optional feedback message to display.
 * @param trailingContent Optional trailing content composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun DivisionListItem(
    divisionCode: String,
    batchCodes: PersistentList<String>,
    semesterNumber: SemesterNumber,
    semesterAcademicStartYear: Int,
    semesterAcademicEndYear: Int,
    branchAbbreviation: String,
    feedback: ListItemFeedback? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        headlineContent = {
            Text(
                text = "Division: $divisionCode",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = "Batches: ${if (batchCodes.isEmpty()) "None" else batchCodes.joinToString(" • ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Sem ${semesterNumber.value} • $semesterAcademicStartYear-$semesterAcademicEndYear",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text(
                        text = branchAbbreviation,
                        style = MaterialTheme.typography.labelSmall
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

@Composable
fun DivisionListItemPreview() {
    MaterialTheme {
        DivisionListItem(
            divisionCode = "A",
            batchCodes = persistentListOf("B1", "B2", "B3"),
            semesterNumber = SemesterNumber.SEMESTER_5,
            semesterAcademicStartYear = 2023,
            semesterAcademicEndYear = 2024,
            branchAbbreviation = "CS",
            onClick = {}
        )
    }
}

@Composable
fun DivisionListItemMultipleBatchesPreview() {
    MaterialTheme {
        DivisionListItem(
            divisionCode = "B",
            batchCodes = persistentListOf("B1", "B2", "B3", "B4"),
            semesterNumber = SemesterNumber.SEMESTER_7,
            semesterAcademicStartYear = 2024,
            semesterAcademicEndYear = 2025,
            branchAbbreviation = "EXTC"
        )
    }
}

@Composable
fun DivisionListItemTwoBatchesPreview() {
    MaterialTheme {
        DivisionListItem(
            divisionCode = "C",
            batchCodes = persistentListOf("B1", "B2"),
            semesterNumber = SemesterNumber.SEMESTER_3,
            semesterAcademicStartYear = 2025,
            semesterAcademicEndYear = 2026,
            branchAbbreviation = "MECH",
            onClick = {}
        )
    }
}
