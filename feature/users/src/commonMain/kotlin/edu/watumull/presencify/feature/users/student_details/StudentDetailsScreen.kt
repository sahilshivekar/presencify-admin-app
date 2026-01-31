package edu.watumull.presencify.feature.users.student_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import edu.watumull.presencify.core.design.systems.Res
import edu.watumull.presencify.core.design.systems.baseline_account_circle_24
import edu.watumull.presencify.core.design.systems.components.*
import edu.watumull.presencify.core.design.systems.components.dialog.PresencifyAlertDialog
import edu.watumull.presencify.core.domain.model.academics.Semester
import edu.watumull.presencify.core.domain.model.student.StudentBatch
import edu.watumull.presencify.core.domain.model.student.StudentDivision
import edu.watumull.presencify.core.presentation.UiConstants
import edu.watumull.presencify.core.presentation.utils.toReadableString
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource

@Composable
fun StudentDetailsScreen(
    state: StudentDetailsState,
    onAction: (StudentDetailsAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(StudentDetailsAction.BackButtonClick) },
        topBarTitle = "Student Details",
    ) { paddingValues ->
        when (state.viewState) {
            is StudentDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is StudentDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is StudentDetailsState.ViewState.Content -> {
                StudentDetailsScreenContent(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(paddingValues)
                )
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
                    DialogIntention.CONFIRM_REMOVE_STUDENT -> {
                        onAction(StudentDetailsAction.ConfirmRemoveStudent)
                    }

                    DialogIntention.GENERIC -> {
                        onAction(StudentDetailsAction.DismissDialog)
                    }
                }
            },
            onDismiss = {
                onAction(StudentDetailsAction.DismissDialog)
            }
        )
    }
}

@Composable
private fun StudentDetailsScreenContent(
    state: StudentDetailsState,
    onAction: (StudentDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = state.student == null,
        transitionSpec = {
            (slideInVertically { it / 5 }).togetherWith(fadeOut())
        }
    ) { isLoading ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StudentImageContainer(state = state, onAction = onAction)

                PersonalDetailsContainer(state = state)

                ContactDetailsContainer(state = state)

                AcademicDetailsContainer(state = state)

                DropoutDetailsContainer(state = state)

                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .widthIn(max = UiConstants.MAX_CONTENT_WIDTH),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PresencifyTextButton(
                            onClick = { onAction(StudentDetailsAction.EditStudentDetailsClick) },
                            enabled = !state.isRemovingStudent
                        ) {
                            Text(text = "Edit details", color = MaterialTheme.colorScheme.primary)
                        }
                        PresencifyTextButton(
                            onClick = { onAction(StudentDetailsAction.RemoveStudentClick) },
                            enabled = !state.isRemovingStudent
                        ) {
                            if (state.isRemovingStudent) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Text(
                                    text = "Remove student",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                SemesterDetailsContainer(state = state)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun StudentImageContainer(
    state: StudentDetailsState,
    onAction: (StudentDetailsAction) -> Unit,
) {
    // Image dialog
    if (state.isImageDialogVisible) {
        Dialog(
            onDismissRequest = { onAction(StudentDetailsAction.ToggleImageDialog) },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = state.student?.studentImageUrl,
                    placeholder = painterResource(Res.drawable.baseline_account_circle_24),
                    fallback = painterResource(Res.drawable.baseline_account_circle_24),
                    error = painterResource(Res.drawable.baseline_account_circle_24),
                    contentDescription = "Student Image",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PresencifyTextButton(
                        onClick = {
                            onAction(StudentDetailsAction.ToggleImageDialog)
                        },
                    ) {
                        Text("Dismiss", color = MaterialTheme.colorScheme.onSurface)
                    }
                    if (state.student?.studentImageUrl != null) {
                        PresencifyTextButton(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                onAction(StudentDetailsAction.RemoveImageClick)
                            },
                            enabled = !state.isUpdatingImage && !state.isRemovingImage
                        ) {
                            if (state.isRemovingImage) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Text(text = "Remove Image", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    } else if (state.newUploadedImageBytes == null) {
                        PresencifyButton(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                // TODO: Implement file picker for image upload
                                // onAction(StudentDetailsAction.StudentNewImageUploaded(imageBytes))
                            },
                            enabled = !state.isUpdatingImage && !state.isRemovingImage,
                            isLoading = false
                        ) {
                            Text(text = "Add Image")
                        }
                    } else {
                        PresencifyButton(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                onAction(StudentDetailsAction.UpdateStudentImageClick)
                            },
                            enabled = !state.isUpdatingImage && !state.isRemovingImage,
                            isLoading = state.isUpdatingImage
                        ) {
                            Text(text = "Upload Image")
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.widthIn(max = UiConstants.MAX_CONTENT_WIDTH),
    ) {
        AsyncImage(
            model = state.student?.studentImageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = if (state.student?.studentImageUrl != null) null else ColorFilter.tint(
                MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
            ),
            placeholder = painterResource(Res.drawable.baseline_account_circle_24),
            error = painterResource(Res.drawable.baseline_account_circle_24),
            fallback = painterResource(Res.drawable.baseline_account_circle_24)
        )

        IconButton(
            onClick = {
                onAction(StudentDetailsAction.ToggleImageDialog)
            },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun PersonalDetailsContainer(state: StudentDetailsState) {
    PresencifyCard(
        modifier = Modifier
            .wrapContentHeight()
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .padding(top = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Personal Details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Full name
        DetailRow(
            label = "Full Name",
            value = buildString {
                append(state.student?.firstName ?: "")
                state.student?.middleName?.let {
                    if (it.isNotBlank()) append(" $it")
                }
                append(" ${state.student?.lastName ?: ""}")
            }.trim()
        )

        // Date of birth
        state.student?.dob?.let { dob ->
            DetailRow(
                label = "Date of Birth",
                value = try {
                    val date = LocalDate.parse(dob)
                    date.toReadableString()
                } catch (_: Exception) {
                    dob
                }
            )
        }

        // Gender
        DetailRow(
            label = "Gender",
            value = state.student?.gender?.toDisplayLabel() ?: "",
            isLast = true
        )
    }
}

@Composable
private fun ContactDetailsContainer(state: StudentDetailsState) {
    PresencifyCard(
        modifier = Modifier
            .wrapContentHeight()
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .padding(top = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Contact Details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        DetailRow(
            label = "Phone number",
            value = state.student?.phoneNumber ?: ""
        )

        DetailRow(
            label = "Email",
            value = state.student?.email ?: ""
        )

        DetailRow(
            label = "Parent's Email",
            value = state.student?.parentEmail ?: "Not added",
            isLast = true
        )
    }
}

@Composable
private fun AcademicDetailsContainer(state: StudentDetailsState) {
    PresencifyCard(
        modifier = Modifier
            .wrapContentHeight()
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .padding(top = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Educational Details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        state.student?.branch?.let {
            DetailRow(
                label = "Branch",
                value = it.abbreviation
            )
        }

        DetailRow(
            label = "PRN",
            value = state.student?.prn ?: ""
        )

        state.student?.scheme?.let {
            DetailRow(
                label = "Scheme",
                value = it.name
            )
        }

        DetailRow(
            label = "Admission Year",
            value = state.student?.admissionYear?.toString() ?: "",
            isLast = true
        )
    }
}

@Composable
private fun DropoutDetailsContainer(state: StudentDetailsState) {
    if (state.dropoutDetails.isNotEmpty()) {
        PresencifyCard(
            modifier = Modifier
                .wrapContentHeight()
                .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
                .padding(top = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Dropout Details",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                state.dropoutDetails.forEach { dropout ->
                    Text(
                        text = dropout.dropoutDate.toReadableString() +
                                (dropout.reason?.let { " - $it" } ?: ""),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SemesterDetailsContainer(state: StudentDetailsState) {
    // Only show if studentSemesters is not null and not empty
    val semesters = state.studentSemesters
    if (semesters.isNullOrEmpty()) return

    PresencifyCard(
        modifier = Modifier
            .wrapContentHeight()
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .padding(vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Past Semesters Details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        val semestersWithData = semesters.mapNotNull { it.semester }

        if (semestersWithData.isEmpty()) {
            Text(
                text = "No semester details available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            semestersWithData.forEach { semester ->
                SemesterItem(
                    semester = semester,
                    studentDivisions = state.studentDivisions,
                    studentBatches = state.studentBatches
                )
            }
        }
    }
}

@Composable
private fun SemesterItem(
    semester: Semester,
    studentDivisions: List<StudentDivision>?,
    studentBatches: List<StudentBatch>?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = semester.semesterNumber.toDisplayLabel(),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "${semester.academicStartYear}-${semester.academicEndYear.toString().takeLast(2)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
        )
    }

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Semester line
        Box(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
                .padding(top = 4.dp, bottom = 20.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        )

        // Semester details column
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Start Date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = ":",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = semester.startDate.toReadableString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Past divisions
            val pastDivisions = studentDivisions
                ?.filter { it.division?.semesterId == semester.id }
                ?.sortedBy { it.startDate }

            pastDivisions?.forEach { studentDivision ->
                DivisionItem(
                    studentDivision = studentDivision,
                    studentBatches = studentBatches
                )
            }

            // Semester end date
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp, bottom = 16.dp)
            ) {
                Text(
                    text = "End Date",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = ":",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = semester.endDate.toReadableString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun DivisionItem(
    studentDivision: StudentDivision,
    studentBatches: List<StudentBatch>?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 32.dp, end = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Division ${studentDivision.division?.divisionCode ?: ""}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 32.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Division line
        Box(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
                .padding(vertical = 4.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium
                )
        )

        // Division details column
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = studentDivision.startDate.toReadableString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Past batches
            val pastBatches = studentBatches
                ?.filter { it.batch?.divisionId == studentDivision.division?.id }
                ?.sortedBy { it.startDate }

            pastBatches?.forEach { studentBatch ->
                BatchItem(studentBatch = studentBatch)
            }

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "till",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = studentDivision.endDate?.toReadableString() ?: "Today",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun BatchItem(studentBatch: StudentBatch) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 32.dp, end = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Batch ${studentBatch.batch?.batchCode ?: ""}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 32.dp)
            .height(IntrinsicSize.Min)
    ) {
        // Batch line
        Box(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
                .padding(vertical = 4.dp)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
        )

        // Batch details column
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "From",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = studentBatch.startDate.toReadableString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "till",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = studentBatch.endDate?.toReadableString() ?: "Today",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = if (isLast) 16.dp else 0.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(.5f),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = ":",
            modifier = Modifier.weight(.05f),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            modifier = Modifier.weight(.5f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


