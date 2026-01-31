package edu.watumull.presencify.feature.academics.search_course

import androidx.compose.runtime.Stable
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.academics.Branch
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.academics.Scheme
import edu.watumull.presencify.core.domain.model.teacher.Teacher
import edu.watumull.presencify.core.presentation.UiText
import edu.watumull.presencify.feature.academics.navigation.SearchCourseIntention
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Stable
data class SearchCourseState(
    val viewState: ViewState = ViewState.Content,
    val dialogState: DialogState? = null,

    // Search & Filter
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,

    // Courses List
    val courses: PersistentList<Course> = persistentListOf(),
    val isLoadingCourses: Boolean = true,

    // Selection Mode
    val intention: SearchCourseIntention = SearchCourseIntention.DEFAULT,
    val branchId: String? = null,
    val semesterNumber: Int? = null,
    val teacherId: String? = null,

    // Filter Options - Semester Number
    val semesterNumberOptions: ImmutableList<SemesterNumber> = SemesterNumber.entries.toImmutableList(),
    val selectedSemesterNumber: SemesterNumber? = null,

    // Filter Options - Branches
    val branchOptions: PersistentList<Branch> = persistentListOf(),
    val selectedBranch: Branch? = null,
    val areBranchesLoading: Boolean = true,

    // Filter Options - Scheme
    val schemeOptions: PersistentList<Scheme> = persistentListOf(),
    val selectedScheme: Scheme? = null,
    val areSchemesLoading: Boolean = true,

    // Filter Options - Teachers
    val teacherOptions: PersistentList<Teacher> = persistentListOf(),
    val selectedTeacherIds: Set<String> = emptySet(),
    val areTeachersLoading: Boolean = true,

    // Pagination
    val currentPage: Int = 1,
    val isLoadingMore: Boolean = false,

    val isBottomSheetVisible: Boolean = false,

    // Track individual course operations (assign/unassign)
    val loadingCourseIds: Set<String> = emptySet()
) {
    sealed interface ViewState {
        data object Loading : ViewState
        data class Error(val message: UiText) : ViewState
        data object Content : ViewState
    }

    data class DialogState(
        val isVisible: Boolean = true,
        val dialogType: DialogType = DialogType.INFO,
        val dialogIntention: DialogIntention = DialogIntention.GENERIC,
        val title: String = "",
        val message: UiText = UiText.DynamicString(""),
    )
}

enum class DialogIntention {
    GENERIC,
}
