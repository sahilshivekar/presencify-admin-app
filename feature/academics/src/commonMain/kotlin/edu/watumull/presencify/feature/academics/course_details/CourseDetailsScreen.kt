package edu.watumull.presencify.feature.academics.course_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
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

@Composable
fun CourseDetailsScreen(
    state: CourseDetailsState,
    onAction: (CourseDetailsAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(CourseDetailsAction.BackButtonClick) },
        topBarTitle = "Course Details",
    ) { paddingValues ->
        when (state.viewState) {
            is CourseDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is CourseDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(text = state.viewState.message.asString())
            }

            is CourseDetailsState.ViewState.Content -> {
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
                    ) {
                        state.course?.let { course ->
                            CourseListItem(
                                name = course.name,
                                code = course.code,
                                schemeName = course.scheme?.name ?: "N/A",
                                optionalCourse = course.optionalCourse,
                                trailingContent = null,
                                onClick = null,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PresencifyTextButton(
                                onClick = { onAction(CourseDetailsAction.EditCourseClick) },
                                enabled = !state.isRemovingCourse
                            ) {
                                androidx.compose.material3.Text(
                                    text = "Edit course",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            PresencifyTextButton(
                                onClick = { onAction(CourseDetailsAction.RemoveCourseClick) },
                                enabled = !state.isRemovingCourse
                            ) {
                                if (state.isRemovingCourse) {
                                    androidx.compose.material3.CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                    )
                                } else {
                                    androidx.compose.material3.Text(
                                        text = "Remove course",
                                        color = MaterialTheme.colorScheme.error
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
                    DialogIntention.CONFIRM_REMOVE_COURSE -> onAction(CourseDetailsAction.ConfirmRemoveCourse)
                    DialogIntention.GENERIC -> onAction(CourseDetailsAction.DismissDialog)
                }
            },
            onDismiss = { onAction(CourseDetailsAction.DismissDialog) }
        )
    }
}

