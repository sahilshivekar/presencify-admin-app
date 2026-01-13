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
import edu.watumull.presencify.core.domain.enums.SemesterNumber

/**
 * List item component for displaying Timetable information.
 *
 * @param branchAbbreviation The abbreviation of the branch.
 * @param year The year (e.g., "TE", "BE").
 * @param semesterNumber The semester number enum.
 * @param semesterAcademicStartYear The academic start year of the semester.
 * @param semesterAcademicEndYear The academic end year of the semester.
 * @param divisionCode The division code.
 * @param trailingContent Optional trailing content composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Composable
fun TimetableListItem(
    branchAbbreviation: String,
    year: String,
    semesterNumber: SemesterNumber,
    semesterAcademicStartYear: Int,
    semesterAcademicEndYear: Int,
    divisionCode: String,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    PresencifyListItem(
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Text(
                        text = year,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Division: $divisionCode",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        supportingContent = {
            Column {
                Text(
                    text = "Sem ${semesterNumber.value} • $semesterAcademicStartYear-$semesterAcademicEndYear",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = branchAbbreviation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        trailingContent = trailingContent,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun TimetableListItemPreview() {
    MaterialTheme {
        TimetableListItem(
            branchAbbreviation = "CS",
            year = "TE",
            semesterNumber = SemesterNumber.SEMESTER_5,
            semesterAcademicStartYear = 2023,
            semesterAcademicEndYear = 2024,
            divisionCode = "A",
            onClick = {}
        )
    }
}

@Composable
fun TimetableListItemBEPreview() {
    MaterialTheme {
        TimetableListItem(
            branchAbbreviation = "EXTC",
            year = "BE",
            semesterNumber = SemesterNumber.SEMESTER_8,
            semesterAcademicStartYear = 2024,
            semesterAcademicEndYear = 2025,
            divisionCode = "B"
        )
    }
}

@Composable
fun TimetableListItemSEPreview() {
    MaterialTheme {
        TimetableListItem(
            branchAbbreviation = "MECH",
            year = "SE",
            semesterNumber = SemesterNumber.SEMESTER_3,
            semesterAcademicStartYear = 2025,
            semesterAcademicEndYear = 2026,
            divisionCode = "C",
            onClick = {}
        )
    }
}

