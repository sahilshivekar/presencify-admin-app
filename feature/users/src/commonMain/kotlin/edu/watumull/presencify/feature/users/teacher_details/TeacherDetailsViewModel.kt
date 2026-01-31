package edu.watumull.presencify.feature.users.teacher_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.users.navigation.UsersRoutes
import kotlinx.coroutines.launch

class TeacherDetailsViewModel(
    private val teacherRepository: TeacherRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TeacherDetailsState, TeacherDetailsEvent, TeacherDetailsAction>(
    initialState = TeacherDetailsState(
        teacherId = savedStateHandle.toRoute<UsersRoutes.TeacherDetails>().teacherId
    )
) {

    init {
        viewModelScope.launch {
            loadTeacherDetails()
            loadAssignedCourses()
        }
    }

    private suspend fun loadTeacherDetails() {
        val teacherId = state.teacherId

        teacherRepository.getTeacherById(teacherId)
            .onSuccess { teacher ->
                updateState {
                    it.copy(
                        viewState = TeacherDetailsState.ViewState.Content,
                        teacher = teacher
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        viewState = TeacherDetailsState.ViewState.Error(error.toUiText())
                    )
                }
            }
    }

    private suspend fun loadAssignedCourses() {
        val teacherId = state.teacherId

        updateState { it.copy(isLoadingAssignedSubjects = true) }

        teacherRepository.getTeachingSubjects(teacherId)
            .onSuccess { courses ->
                updateState {
                    it.copy(
                        assignedCourses = courses,
                        isLoadingAssignedSubjects = false
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isLoadingAssignedSubjects = false,
                        dialogState = TeacherDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Loading Assigned Courses",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    override fun handleAction(action: TeacherDetailsAction) {
        when (action) {
            is TeacherDetailsAction.BackButtonClick -> {
                sendEvent(TeacherDetailsEvent.NavigateBack)
            }

            is TeacherDetailsAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }

            is TeacherDetailsAction.ToggleImageDialog -> {
                val newVisibility = !state.isImageDialogVisible
                updateState {
                    it.copy(
                        isImageDialogVisible = newVisibility,
                        newUploadedImageBytes = if (!newVisibility) null else it.newUploadedImageBytes
                    )
                }
            }

            is TeacherDetailsAction.TeacherNewImageUploaded -> {
                updateState {
                    it.copy(newUploadedImageBytes = action.imageBytes)
                }
            }

            is TeacherDetailsAction.UpdateTeacherImageClick -> {
                viewModelScope.launch {
                    updateTeacherImage()
                }
            }

            is TeacherDetailsAction.RemoveImageClick -> {
                viewModelScope.launch {
                    removeTeacherImage()
                }
            }

            is TeacherDetailsAction.RemoveTeacherClick -> {
                updateState {
                    it.copy(
                        dialogState = TeacherDetailsState.DialogState(
                            dialogType = DialogType.CONFIRM_RISKY_ACTION,
                            dialogIntention = DialogIntention.CONFIRM_REMOVE_TEACHER,
                            title = "Remove Teacher",
                            message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                                "Are you sure you want to remove ${state.teacher?.firstName} ${state.teacher?.lastName}?"
                            )
                        )
                    )
                }
            }

            is TeacherDetailsAction.EditTeacherDetailsClick -> {
                sendEvent(TeacherDetailsEvent.NavigateToEditTeacher(state.teacherId))
            }
        }
    }

    private suspend fun updateTeacherImage() {
        val imageBytes = state.newUploadedImageBytes
        val teacherId = state.teacher?.id

        if (imageBytes == null || teacherId == null) return

        updateState { it.copy(isUpdatingImage = true) }

        teacherRepository.updateTeacherImage(teacherId, imageBytes)
            .onSuccess { updatedTeacher ->
                updateState {
                    it.copy(
                        isUpdatingImage = false,
                        isImageDialogVisible = false,
                        newUploadedImageBytes = null,
                        teacher = updatedTeacher
                    )
                }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Image updated successfully")
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isUpdatingImage = false,
                        isImageDialogVisible = false,
                        newUploadedImageBytes = null,
                        dialogState = TeacherDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Updating Image",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    private suspend fun removeTeacherImage() {
        val teacherId = state.teacher?.id ?: return

        updateState { it.copy(isRemovingImage = true) }

        teacherRepository.removeTeacherImage(teacherId)
            .onSuccess { updatedTeacher ->
                updateState {
                    it.copy(
                        isRemovingImage = false,
                        isImageDialogVisible = false,
                        newUploadedImageBytes = null,
                        teacher = updatedTeacher
                    )
                }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Image removed successfully")
                    )
                }
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isRemovingImage = false,
                        isImageDialogVisible = false,
                        newUploadedImageBytes = null,
                        dialogState = TeacherDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing Image",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }

    fun confirmRemoveTeacher() {
        viewModelScope.launch {
            removeTeacher()
        }
    }

    private suspend fun removeTeacher() {
        val teacherId = state.teacher?.id ?: return

        updateState {
            it.copy(
                isRemovingTeacher = true,
                dialogState = null
            )
        }
        teacherRepository.removeTeacher(teacherId)
            .onSuccess {
                updateState { it.copy(isRemovingTeacher = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Teacher removed successfully")
                    )
                }
                sendEvent(TeacherDetailsEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isRemovingTeacher = false,
                        dialogState = TeacherDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing Teacher",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

