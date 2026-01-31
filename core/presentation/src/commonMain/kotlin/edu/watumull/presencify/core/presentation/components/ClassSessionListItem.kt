package edu.watumull.presencify.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyListItem
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.presentation.utils.DateTimeUtils
import edu.watumull.presencify.core.presentation.utils.toReadableString
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

/**
 * List item component for displaying ClassSession information.
 *
 * @param courseName The name of the course.
 * @param classType The type of class (Lecture, Tutorial, Practical).
 * @param dayOfWeek The day of the week.
 * @param startTime The start time of the class.
 * @param endTime The end time of the class.
 * @param roomNumber The room number where the class is held.
 * @param teacherName The name of the teacher.
 * @param semesterNumber The semester number.
 * @param semesterAcademicStartYear The academic start year of the semester.
 * @param semesterAcademicEndYear The academic end year of the semester.
 * @param year The year (e.g., "TE", "BE").
 * @param branchAbbreviation The branch abbreviation.
 * @param activeFrom The date from which this class is active.
 * @param activeTill The date till which this class is active.
 * @param batchCode Optional batch code if the class is for a specific batch.
 * @param divisionCode Optional division code if the class is for a specific division.
 * @param isExtraClass Whether this is an extra class.
 * @param feedback Optional feedback message to display.
 * @param trailingContent Optional trailing content composable.
 * @param onClick Optional click handler for the list item.
 * @param modifier Modifier for the list item.
 */
@Suppress("DEPRECATION")
@Composable
fun ClassSessionListItem(
    courseName: String,
    classType: ClassType,
    dayOfWeek: DayOfWeek,
    startTime: LocalTime,
    endTime: LocalTime,
    roomNumber: String,
    teacherName: String,
    semesterNumber: SemesterNumber,
    semesterAcademicStartYear: Int,
    semesterAcademicEndYear: Int,
    year: String,
    branchAbbreviation: String,
    activeFrom: LocalDate,
    activeTill: LocalDate,
    batchCode: String? = null,
    divisionCode: String? = null,
    isExtraClass: Boolean = false,
    feedback: ListItemFeedback? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val today = DateTimeUtils.getCurrentDate()
    val isNotActive = activeTill < today

    PresencifyListItem(
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = courseName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Badge(
                    containerColor = when (classType) {
                        ClassType.LECTURE -> MaterialTheme.colorScheme.primaryContainer
                        ClassType.TUTORIAL -> MaterialTheme.colorScheme.secondaryContainer
                        ClassType.PRACTICAL -> MaterialTheme.colorScheme.tertiaryContainer
                    },
                    contentColor = when (classType) {
                        ClassType.LECTURE -> MaterialTheme.colorScheme.onPrimaryContainer
                        ClassType.TUTORIAL -> MaterialTheme.colorScheme.onSecondaryContainer
                        ClassType.PRACTICAL -> MaterialTheme.colorScheme.onTertiaryContainer
                    }
                ) {
                    Text(
                        text = classType.toDisplayLabel(),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        },
        supportingContent = {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.width(16.dp).height(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${dayOfWeek.toDisplayLabel()} • ${startTime.toReadableString()} - ${endTime.toReadableString()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "Room: $roomNumber • $teacherName",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                batchCode?.let {
                    Text(
                        text = "Batch: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                divisionCode?.let {
                    Text(
                        text = "Division: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "Sem ${semesterNumber.value} • $semesterAcademicStartYear-$semesterAcademicEndYear",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Active: ${activeFrom.toReadableString()} - ${activeTill.toReadableString()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Text(
                            text = "$year $branchAbbreviation",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    if (isExtraClass) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Badge(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                        ) {
                            Text(
                                text = "Extra Class",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    if (isNotActive) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Badge(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ) {
                            Text(
                                text = "Not Active",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
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
fun ClassSessionListItemLecturePreview() {
    MaterialTheme {
        ClassSessionListItem(
            courseName = "Data Structures",
            classType = ClassType.LECTURE,
            dayOfWeek = DayOfWeek.MONDAY,
            startTime = LocalTime(9, 0),
            endTime = LocalTime(10, 0),
            roomNumber = "101",
            teacherName = "Dr. Smith",
            semesterNumber = SemesterNumber.SEMESTER_5,
            semesterAcademicStartYear = 2025,
            semesterAcademicEndYear = 2026,
            year = "TE",
            branchAbbreviation = "CS",
            activeFrom = LocalDate(2025, 7, 1),
            activeTill = LocalDate(2025, 12, 31),
            divisionCode = "A",
            onClick = {}
        )
    }
}

@Composable
fun ClassSessionListItemPracticalPreview() {
    MaterialTheme {
        ClassSessionListItem(
            courseName = "Computer Networks Lab",
            classType = ClassType.PRACTICAL,
            dayOfWeek = DayOfWeek.WEDNESDAY,
            startTime = LocalTime(14, 0),
            endTime = LocalTime(16, 0),
            roomNumber = "Lab-302",
            teacherName = "Prof. Johnson",
            semesterNumber = SemesterNumber.SEMESTER_7,
            semesterAcademicStartYear = 2025,
            semesterAcademicEndYear = 2026,
            year = "BE",
            branchAbbreviation = "EXTC",
            activeFrom = LocalDate(2025, 7, 15),
            activeTill = LocalDate(2026, 6, 30),
            batchCode = "B1"
        )
    }
}

@Composable
fun ClassSessionListItemExtraClassPreview() {
    MaterialTheme {
        ClassSessionListItem(
            courseName = "Machine Learning",
            classType = ClassType.TUTORIAL,
            dayOfWeek = DayOfWeek.SATURDAY,
            startTime = LocalTime(10, 0),
            endTime = LocalTime(12, 0),
            roomNumber = "205",
            teacherName = "Dr. Williams",
            semesterNumber = SemesterNumber.SEMESTER_6,
            semesterAcademicStartYear = 2024,
            semesterAcademicEndYear = 2025,
            year = "TE",
            branchAbbreviation = "CS",
            activeFrom = LocalDate(2024, 8, 1),
            activeTill = LocalDate(2026, 1, 13),
            batchCode = "B2",
            isExtraClass = true,
            onClick = {}
        )
    }
}
