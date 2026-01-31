package edu.watumull.presencify.feature.schedule.search_class

import androidx.compose.runtime.Stable
import edu.watumull.presencify.core.design.systems.components.dialog.DialogType
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import edu.watumull.presencify.core.domain.model.academics.Batch
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import edu.watumull.presencify.core.domain.model.schedule.Room
import edu.watumull.presencify.core.domain.model.schedule.Timetable
import edu.watumull.presencify.core.domain.model.teacher.Teacher
import edu.watumull.presencify.core.presentation.UiText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Stable
data class SearchClassState(
    val viewState: ViewState = ViewState.Content,
    val dialogState: DialogState? = null,

    // Search & Filter
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,

    // Classes List
    val classes: PersistentList<ClassSession> = persistentListOf(),
    val isLoadingClasses: Boolean = true,

    // Filter Options - Timetable
    val timetableOptions: PersistentList<Timetable> = persistentListOf(),
    val selectedTimetable: Timetable? = null,
    val areTimetablesLoading: Boolean = true,

    // Filter Options - Teacher
    val teacherOptions: PersistentList<Teacher> = persistentListOf(),
    val selectedTeacher: Teacher? = null,
    val areTeachersLoading: Boolean = true,

    // Filter Options - Room
    val roomOptions: PersistentList<Room> = persistentListOf(),
    val selectedRoom: Room? = null,
    val areRoomsLoading: Boolean = true,

    // Filter Options - Batch
    val batchOptions: PersistentList<Batch> = persistentListOf(),
    val selectedBatch: Batch? = null,
    val areBatchesLoading: Boolean = true,

    // Filter Options - Course
    val courseOptions: PersistentList<Course> = persistentListOf(),
    val selectedCourse: Course? = null,
    val areCoursesLoading: Boolean = true,

    // Filter Options - Day of Week
    val dayOfWeekOptions: ImmutableList<DayOfWeek> = DayOfWeek.entries.toImmutableList(),
    val selectedDayOfWeek: DayOfWeek? = null,

    // Filter Options - Class Type
    val classTypeOptions: ImmutableList<ClassType> = ClassType.entries.toImmutableList(),
    val selectedClassType: ClassType? = null,

    // Filter Options - Extra Class
    val isExtraClass: Boolean? = null,

    // Pagination
    val currentPage: Int = 1,
    val isLoadingMore: Boolean = false,

    val isBottomSheetVisible: Boolean = false
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

