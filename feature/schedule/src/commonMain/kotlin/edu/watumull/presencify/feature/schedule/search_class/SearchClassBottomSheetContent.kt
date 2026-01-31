package edu.watumull.presencify.feature.schedule.search_class

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.watumull.presencify.core.design.systems.components.PresencifyButton
import edu.watumull.presencify.core.domain.enums.ClassType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchClassBottomSheetContent(
    state: SearchClassState,
    onAction: (SearchClassAction) -> Unit,
    onDismiss: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Fixed Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Filter", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Reset",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    onAction(SearchClassAction.ResetFilters)
                }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        // Scrollable Filter Options
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Teacher Filter
            FilterSection(
                title = "Teacher",
                isLoading = state.areTeachersLoading
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.teacherOptions.forEach { teacher ->
                        FilterChip(
                            selected = state.selectedTeacher == teacher,
                            onClick = {
                                val newTeacher = if (state.selectedTeacher == teacher) null else teacher
                                onAction(SearchClassAction.SelectTeacher(newTeacher))
                            },
                            label = { Text("${teacher.firstName} ${teacher.lastName}") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Room Filter
            FilterSection(
                title = "Room",
                isLoading = state.areRoomsLoading
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.roomOptions.forEach { room ->
                        FilterChip(
                            selected = state.selectedRoom == room,
                            onClick = {
                                val newRoom = if (state.selectedRoom == room) null else room
                                onAction(SearchClassAction.SelectRoom(newRoom))
                            },
                            label = { Text(room.roomNumber) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Batch Filter
            FilterSection(
                title = "Batch",
                isLoading = state.areBatchesLoading
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.batchOptions.forEach { batch ->
                        FilterChip(
                            selected = state.selectedBatch == batch,
                            onClick = {
                                val newBatch = if (state.selectedBatch == batch) null else batch
                                onAction(SearchClassAction.SelectBatch(newBatch))
                            },
                            label = { Text(batch.batchCode) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Course Filter
            FilterSection(
                title = "Course",
                isLoading = state.areCoursesLoading
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.courseOptions.forEach { course ->
                        FilterChip(
                            selected = state.selectedCourse == course,
                            onClick = {
                                val newCourse = if (state.selectedCourse == course) null else course
                                onAction(SearchClassAction.SelectCourse(newCourse))
                            },
                            label = { Text(course.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Day of Week Filter
            FilterSection(title = "Day of Week") {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.dayOfWeekOptions.forEach { dayOfWeek ->
                        FilterChip(
                            selected = state.selectedDayOfWeek == dayOfWeek,
                            onClick = {
                                val newDayOfWeek = if (state.selectedDayOfWeek == dayOfWeek) null else dayOfWeek
                                onAction(SearchClassAction.SelectDayOfWeek(newDayOfWeek))
                            },
                            label = { Text(dayOfWeek.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Class Type Filter
            FilterSection(title = "Class Type") {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.classTypeOptions.forEach { classType ->
                        val displayText = when (classType) {
                            ClassType.LECTURE -> "Lecture"
                            ClassType.PRACTICAL -> "Practical"
                            ClassType.TUTORIAL -> "Tutorial"
                        }
                        FilterChip(
                            selected = state.selectedClassType == classType,
                            onClick = {
                                val newClassType = if (state.selectedClassType == classType) null else classType
                                onAction(SearchClassAction.SelectClassType(newClassType))
                            },
                            label = { Text(displayText) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Extra Class Filter
            FilterSection(title = "Extra Class") {
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = state.isExtraClass == true,
                        onClick = {
                            val newValue = if (state.isExtraClass == true) null else true
                            onAction(SearchClassAction.ToggleIsExtraClass(newValue))
                        },
                        label = { Text("Extra Class") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    FilterChip(
                        selected = state.isExtraClass == false,
                        onClick = {
                            val newValue = if (state.isExtraClass == false) null else false
                            onAction(SearchClassAction.ToggleIsExtraClass(newValue))
                        },
                        label = { Text("Regular Class") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }

        // Apply Button
        PresencifyButton(
            onClick = {
                onAction(SearchClassAction.ApplyFilters)
                onDismiss()
            },
            text = "Apply Filters",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun FilterSection(
    title: String,
    isLoading: Boolean = false,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            content()
        }
    }
}

