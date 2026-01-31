package edu.watumull.presencify.feature.academics.semester_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.SemesterRepository
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarController
import edu.watumull.presencify.core.presentation.global_snackbar.SnackbarEvent
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import kotlinx.coroutines.launch

class SemesterDetailsViewModel(
    private val semesterRepository: SemesterRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SemesterDetailsState, SemesterDetailsEvent, SemesterDetailsAction>(
    initialState = SemesterDetailsState(
        semesterId = savedStateHandle.toRoute<AcademicsRoutes.SemesterDetails>().semesterId
    )
) {

    init {
        viewModelScope.launch {
            loadSemester()
            loadCoursesOfSemester()
        }
    }

    private suspend fun loadSemester() {
        val semesterId = state.semesterId

        semesterRepository.getSemesterById(semesterId)
            .onSuccess { semester ->
                updateState { it.copy(viewState = SemesterDetailsState.ViewState.Content, semester = semester) }
            }
            .onError { error ->
                updateState { it.copy(viewState = SemesterDetailsState.ViewState.Error(error.toUiText())) }
            }
    }

    private suspend fun loadCoursesOfSemester() {
        val semesterId = state.semesterId

        updateState { it.copy(isLoadingCourses = true) }

        semesterRepository.getCoursesOfSemester(semesterId)
            .onSuccess { courses ->
                updateState { it.copy(courses = courses, isLoadingCourses = false) }
            }
            .onError { _ ->
                updateState { it.copy(isLoadingCourses = false) }
                // Don't show error dialog for courses - just leave the list empty
            }
    }

    override fun handleAction(action: SemesterDetailsAction) {
        when (action) {
            is SemesterDetailsAction.BackButtonClick -> sendEvent(SemesterDetailsEvent.NavigateBack)
            is SemesterDetailsAction.DismissDialog -> updateState { it.copy(dialogState = null) }
            is SemesterDetailsAction.RemoveSemesterClick -> updateState {
                it.copy(
                    dialogState = SemesterDetailsState.DialogState(
                        dialogType = DialogType.CONFIRM_RISKY_ACTION,
                        dialogIntention = DialogIntention.CONFIRM_REMOVE_SEMESTER,
                        title = "Remove Semester",
                        message = edu.watumull.presencify.core.presentation.UiText.DynamicString(
                            "Are you sure you want to remove this semester?, This will also remove all associated divisions and batches"
                        )
                    )
                )
            }
            is SemesterDetailsAction.ConfirmRemoveSemester -> {
                viewModelScope.launch {
                    removeSemester()
                }
            }
            is SemesterDetailsAction.EditSemesterClick -> sendEvent(SemesterDetailsEvent.NavigateToEditSemester(state.semesterId))
        }
    }

    private suspend fun removeSemester() {
        val semesterId = state.semester?.id ?: return

        updateState { it.copy(isRemovingSemester = true, dialogState = null) }

        semesterRepository.removeSemester(semesterId)
            .onSuccess {
                updateState { it.copy(isRemovingSemester = false) }
                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(message = "Semester removed successfully")
                    )
                }
                sendEvent(SemesterDetailsEvent.NavigateBack)
            }
            .onError { error ->
                updateState {
                    it.copy(
                        isRemovingSemester = false,
                        dialogState = SemesterDetailsState.DialogState(
                            dialogType = DialogType.ERROR,
                            dialogIntention = DialogIntention.GENERIC,
                            title = "Error Removing Semester",
                            message = error.toUiText()
                        )
                    )
                }
            }
    }
}

