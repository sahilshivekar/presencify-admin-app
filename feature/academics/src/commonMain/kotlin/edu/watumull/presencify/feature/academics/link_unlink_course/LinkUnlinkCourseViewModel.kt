package edu.watumull.presencify.feature.academics.link_unlink_course

import androidx.lifecycle.viewModelScope
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.onError
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository
import edu.watumull.presencify.core.presentation.toUiText
import edu.watumull.presencify.core.presentation.utils.BaseViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

class LinkUnlinkCourseViewModel(
    private val branchRepository: BranchRepository,
) : BaseViewModel<LinkUnlinkCourseState, LinkUnlinkCourseEvent, LinkUnlinkCourseAction>(
    initialState = LinkUnlinkCourseState()
) {

    init {
        loadBranches()
    }

    private fun loadBranches() {
        viewModelScope.launch {
            updateState { it.copy(areBranchesLoading = true) }
            branchRepository.getBranches(searchQuery = null)
                .onSuccess { branches ->
                    updateState {
                        it.copy(
                            branchOptions = branches.toPersistentList(),
                            areBranchesLoading = false
                        )
                    }
                }
                .onError { error ->
                    updateState {
                        it.copy(
                            areBranchesLoading = false,
                            dialogState = LinkUnlinkCourseState.DialogState(
                                dialogType = DialogType.ERROR,
                                title = "Error",
                                message = error.toUiText(),
                                dialogIntention = DialogIntention.GENERIC
                            )
                        )
                    }
                }
        }
    }

    private fun validateForm(): Boolean {
        val branch = state.selectedBranch
        val semesterNumber = state.selectedSemesterNumber

        var hasError = false

        if (branch == null) {
            updateState { it.copy(branchError = "Please select a branch") }
            hasError = true
        } else {
            updateState { it.copy(branchError = null) }
        }

        if (semesterNumber == null) {
            updateState { it.copy(semesterError = "Please select a semester") }
            hasError = true
        } else {
            updateState { it.copy(semesterError = null) }
        }

        return !hasError
    }

    override fun handleAction(action: LinkUnlinkCourseAction) {
        when (action) {
            is LinkUnlinkCourseAction.BackButtonClick -> {
                sendEvent(LinkUnlinkCourseEvent.NavigateBack)
            }

            is LinkUnlinkCourseAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }

            is LinkUnlinkCourseAction.SelectBranch -> {
                updateState {
                    it.copy(
                        selectedBranch = action.branch,
                        branchError = null
                    )
                }
            }

            is LinkUnlinkCourseAction.SelectSemesterNumber -> {
                updateState {
                    it.copy(
                        selectedSemesterNumber = action.semesterNumber,
                        semesterError = null
                    )
                }
            }

            is LinkUnlinkCourseAction.ChangeBranchDropDownVisibility -> {
                updateState { it.copy(isBranchDropdownOpen = action.isOpen) }
            }

            is LinkUnlinkCourseAction.ChangeSemesterDropDownVisibility -> {
                updateState { it.copy(isSemesterDropdownOpen = action.isOpen) }
            }

            is LinkUnlinkCourseAction.LinkCoursesClick -> {
                if (!validateForm()) return

                val branchId = state.selectedBranch?.id ?: return
                val semesterNumber = state.selectedSemesterNumber?.value ?: return

                sendEvent(
                    LinkUnlinkCourseEvent.NavigateToSearchCourse(
                        branchId = branchId,
                        semesterNumber = semesterNumber
                    )
                )
            }
        }
    }
}
