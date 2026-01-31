package edu.watumull.presencify.feature.academics.semester_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.watumull.presencify.core.design.systems.components.PresencifyDefaultLoadingScreen
import edu.watumull.presencify.core.design.systems.components.PresencifyNoResultsIndicator
import edu.watumull.presencify.core.design.systems.components.PresencifyScaffold
import edu.watumull.presencify.core.design.systems.components.PresencifyTextButton
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.components.CourseListItem
import edu.watumull.presencify.core.presentation.components.SemesterListItem
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SemesterDetailsScreen(
    state: SemesterDetailsState,
    onAction: (SemesterDetailsAction) -> Unit,
    onConfirmRemove: () -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(SemesterDetailsAction.BackButtonClick) },
        topBarTitle = "Semester Details",
    ) { paddingValues ->
        when (state.viewState) {
            is SemesterDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is SemesterDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is SemesterDetailsState.ViewState.Content -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {



                    Column(
                        modifier = Modifier
                            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Semester",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        state.semester?.let { semester ->
                            val divisionCodes = semester.divisions?.map { it.divisionCode }?.toPersistentList() ?: kotlinx.collections.immutable.persistentListOf()
                            val batchCodes = semester.divisions?.flatMap { division ->
                                division.batches?.map { it.batchCode } ?: emptyList()
                            }?.distinct()?.toPersistentList() ?: kotlinx.collections.immutable.persistentListOf()
                            val branchAbbreviation = semester.branch?.abbreviation ?: "N/A"

                            SemesterListItem(
                                semesterNumber = semester.semesterNumber,
                                semesterAcademicStartYear = semester.academicStartYear,
                                semesterAcademicEndYear = semester.academicEndYear,
                                divisionCodes = divisionCodes,
                                batchCodes = batchCodes,
                                branchAbbreviation = branchAbbreviation,
                                onClick = { },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PresencifyTextButton(
                                onClick = { onAction(SemesterDetailsAction.EditSemesterClick) },
                                enabled = !state.isRemovingSemester
                            ) {
                                Text(text = "Edit semester", color = MaterialTheme.colorScheme.primary)
                            }

                            PresencifyTextButton(
                                onClick = { onAction(SemesterDetailsAction.RemoveSemesterClick) },
                                enabled = !state.isRemovingSemester
                            ) {
                                if (state.isRemovingSemester) {
                                    CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                    )
                                } else {
                                    Text(text = "Remove semester", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Courses Section
                        Text(
                            text = "Courses of this semester",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        if (state.isLoadingCourses) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        } else if (state.courses.isEmpty()) {
                            PresencifyNoResultsIndicator(
                                text = "No courses found for this semester"
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(
                                    items = state.courses,
                                    key = { course -> course.id }
                                ) { course ->
                                    CourseListItem(
                                        name = course.name,
                                        code = course.code,
                                        schemeName = course.scheme?.name ?: "N/A",
                                        optionalCourse = course.optionalCourse,
                                        onClick = { },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    state.dialogState?.let { dialogState ->
        PresencifyAlertDialog(
            isVisible = dialogState.isVisible,
            dialogType = dialogState.dialogType,
            title = dialogState.title,
            message = dialogState.message?.asString() ?: "",
            onConfirm = {
                when (dialogState.dialogIntention) {
                    DialogIntention.CONFIRM_REMOVE_SEMESTER -> onConfirmRemove()
                    DialogIntention.GENERIC -> onAction(SemesterDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(SemesterDetailsAction.DismissDialog) }
        )
    }
}
