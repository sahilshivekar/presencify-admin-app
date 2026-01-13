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
import edu.watumull.presencify.core.domain.enums.SemesterNumber

/**
 * List item component for displaying Batch information.
 *
 * @param batchCode The batch code.
 * @param divisionCode The division code that this batch belongs to.
 * @param semesterNumber The semester number.
 * @param semesterAcademicStartYear The academic start year of the semester.
 * @param semesterAcademicEndYear The academic end year of the semester.
 * @param branchAbbreviation The branch abbreviation.
 * @param trailingContent Optional trailing content composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun BatchListItem(
    batchCode: String,
    divisionCode: String,
    semesterNumber: SemesterNumber,
    semesterAcademicStartYear: Int,
    semesterAcademicEndYear: Int,
    branchAbbreviation: String,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        headlineContent = {
            Text(
                text = "Batch: $batchCode",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        supportingContent = {
            Column {
                Text(
                    text = "Division: $divisionCode",
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
            }
        },
        trailingContent = trailingContent,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun BatchListItemPreview() {
    MaterialTheme {
        BatchListItem(
            batchCode = "B1",
            divisionCode = "A",
            semesterNumber = SemesterNumber.SEMESTER_5,
            semesterAcademicStartYear = 2023,
            semesterAcademicEndYear = 2024,
            branchAbbreviation = "CS",
            onClick = {}
        )
    }
}

@Composable
fun BatchListItemB2Preview() {
    MaterialTheme {
        BatchListItem(
            batchCode = "B2",
            divisionCode = "B",
            semesterNumber = SemesterNumber.SEMESTER_7,
            semesterAcademicStartYear = 2024,
            semesterAcademicEndYear = 2025,
            branchAbbreviation = "EXTC"
        )
    }
}

@Composable
fun BatchListItemB3Preview() {
    MaterialTheme {
        BatchListItem(
            batchCode = "B3",
            divisionCode = "C",
            semesterNumber = SemesterNumber.SEMESTER_3,
            semesterAcademicStartYear = 2025,
            semesterAcademicEndYear = 2026,
            branchAbbreviation = "MECH",
            onClick = {}
        )
    }
}
