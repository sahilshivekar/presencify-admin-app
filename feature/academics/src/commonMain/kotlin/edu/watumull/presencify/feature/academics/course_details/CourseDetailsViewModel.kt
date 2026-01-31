package edu.watumull.presencify.feature.academics.course_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class CourseDetailsViewModel(
    private val courseRepository: CourseRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<CourseDetailsState, CourseDetailsEvent, CourseDetailsAction>(
    initialState = CourseDetailsState(
        courseId = savedStateHandle.toRoute<AcademicsRoutes.CourseDetails>().courseId
    )
) {

    init {
        viewModelScope.launch {
            loadCourse()
        }
    }

    private suspend fun loadCourse() {
        val courseId = state.courseId

        courseRepository.getCourseById(courseId)
            .onSuccess { course ->
                updateState { it.copy(viewState = CourseDetailsState.ViewState.Content, course = course) }
            }
            .onError { error ->
                updateState { it.copy(viewState = CourseDetailsState.ViewState.Error(error.toUiText())) }
            }
    }

    override fun handleAction(action: CourseDetailsAction) {
        when (action) {
            is CourseDetailsAction.BackButtonClick -> sendEvent(CourseDetailsEvent.NavigateBack)
            is CourseDetailsAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is CourseDetailsAction.RemoveCourseClick -> updateState {
                it.copy(
                    dialogState = CourseDetailsState.DialogState(
                        dialogType = DialogType.CONFIRM_RISKY_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_REMOVE_COURSE,
                        title = "Remove Course",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                            "Are you sure you want to remove ${state.course?.name ?: "this course"}?"
                        )
                    )
                )
            }
            is CourseDetailsAction.EditCourseClick -> sendEvent(CourseDetailsEvent.NavigateToEditCourse(state.courseId))
        }
    }

    fun confirmRemoveCourse() {
        viewModelScope.launch {
            removeCourse()
        }
    }

    private suspend fun removeCourse() {
        val courseId = state.course?.id ?: return

        updateState { it.copy(isRemovingCourse = true, dialogState = null) }

        courseRepository.removeCourse(courseId)
            .onSuccess {
                updateState { it.copy(isRemovingCourse = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Course removed successfully")
                    )
                }
                sendEvent(CourseDetailsEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isRemovingCourse = false,
                        dialogState = CourseDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing Course",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

