package edu.watumull.presencify.feature.users.teacher_details

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
import edu.watumull.presencify.core.presentation.UiConstants
import org.jetbrains.compose.resources.painterResource

@Composable
fun TeacherDetailsScreen(
    state: TeacherDetailsState,
    onAction: (TeacherDetailsAction) -> Unit,
) {
    PresencifyScaffold(
        backPress = { onAction(TeacherDetailsAction.BackButtonClick) },
        topBarTitle = "Teacher Details",
    ) { paddingValues ->
        when (state.viewState) {
            is TeacherDetailsState.ViewState.Loading -> {
                PresencifyDefaultLoadingScreen()
            }

            is TeacherDetailsState.ViewState.Error -> {
                PresencifyNoResultsIndicator(
                    text = state.viewState.message.asString()
                )
            }

            is TeacherDetailsState.ViewState.Content -> {
                TeacherDetailsScreenContent(
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
                    DialogIntention.CONFIRM_REMOVE_TEACHER -> {
                        onAction(TeacherDetailsAction.ConfirmRemoveTeacher)
                    }

                    DialogIntention.GENERIC -> {
                        onAction(TeacherDetailsAction.DismissDialog)
                    }
                }
            },
            onDismiss = {
                onAction(TeacherDetailsAction.DismissDialog)
            }
        )
    }
}

@Composable
private fun TeacherDetailsScreenContent(
    state: TeacherDetailsState,
    onAction: (TeacherDetailsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeacherImageContainer(state = state, onAction = onAction)

        Spacer(modifier = Modifier.height(16.dp))

        TeacherDetailsContainer(state = state)

        Spacer(modifier = Modifier.height(16.dp))


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
                    onClick = { onAction(TeacherDetailsAction.EditTeacherDetailsClick) },
                    enabled = !state.isRemovingTeacher
                ) {
                    Text(text = "Edit details", color = MaterialTheme.colorScheme.primary)
                }
                PresencifyTextButton(
                    onClick = { onAction(TeacherDetailsAction.RemoveTeacherClick) },
                    enabled = !state.isRemovingTeacher
                ) {
                    if (state.isRemovingTeacher) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            text = "Remove teacher",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            // Assign/Unassign Courses Action Bar
            PresencifyActionBar(
                text = "Assign / Unassign Courses",
                onClick = { onAction(TeacherDetailsAction.AssignUnassignCoursesClick) },
                leadingImageVector = Icons.Default.Edit,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun TeacherImageContainer(
    state: TeacherDetailsState,
    onAction: (TeacherDetailsAction) -> Unit,
) {
    // Image dialog
    if (state.isImageDialogVisible) {
        Dialog(
            onDismissRequest = { onAction(TeacherDetailsAction.ToggleImageDialog) },
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
                    model = state.teacher?.teacherImageUrl,
                    placeholder = painterResource(Res.drawable.baseline_account_circle_24),
                    fallback = painterResource(Res.drawable.baseline_account_circle_24),
                    error = painterResource(Res.drawable.baseline_account_circle_24),
                    contentDescription = "Teacher Image",
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
                            onAction(TeacherDetailsAction.ToggleImageDialog)
                        },
                    ) {
                        Text("Dismiss", color = MaterialTheme.colorScheme.onSurface)
                    }
                    if (state.teacher?.teacherImageUrl != null) {
                        PresencifyTextButton(
                            modifier = Modifier.wrapContentSize(),
                            onClick = {
                                onAction(TeacherDetailsAction.RemoveImageClick)
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
                                // onAction(TeacherDetailsAction.TeacherNewImageUploaded(imageBytes))
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
                                onAction(TeacherDetailsAction.UpdateTeacherImageClick)
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
            model = state.teacher?.teacherImageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            colorFilter = if (state.teacher?.teacherImageUrl != null) null else ColorFilter.tint(
                MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)
            ),
            placeholder = painterResource(Res.drawable.baseline_account_circle_24),
            error = painterResource(Res.drawable.baseline_account_circle_24),
            fallback = painterResource(Res.drawable.baseline_account_circle_24)
        )

        IconButton(
            onClick = {
                onAction(TeacherDetailsAction.ToggleImageDialog)
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
private fun TeacherDetailsContainer(state: TeacherDetailsState) {
    PresencifyCard(
        modifier = Modifier
            .wrapContentHeight()
            .widthIn(max = UiConstants.MAX_CONTENT_WIDTH)
            .padding(top = 16.dp),
    ) {
        DetailRow(
            label = "Full Name",
            value = buildString {
                append(state.teacher?.firstName ?: "")
                state.teacher?.middleName?.let {
                    if (it.isNotBlank()) append(" $it")
                }
                append(" ${state.teacher?.lastName ?: ""}")
            }.trim()
        )

        DetailRow(
            label = "Gender",
            value = state.teacher?.gender?.toDisplayLabel() ?: ""
        )

        DetailRow(
            label = "Email",
            value = state.teacher?.email ?: ""
        )

        DetailRow(
            label = "Phone",
            value = state.teacher?.phoneNumber ?: ""
        )

        DetailRow(
            label = "Qualification",
            value = state.teacher?.highestQualification ?: "Not added"
        )

        DetailRow(
            label = "Role",
            value = state.teacher?.role?.toDisplayLabel() ?: "",
            isLast = true
        )
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

