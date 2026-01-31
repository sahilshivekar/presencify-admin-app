package edu.watumull.presencify.feature.schedule.search_class

import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import edu.watumull.presencify.core.domain.model.academics.Batch
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.schedule.Room
import edu.watumull.presencify.core.domain.model.teacher.Teacher

sealed interface SearchClassAction {
    data object BackButtonClick : SearchClassAction
    data object DismissDialog : SearchClassAction

    data object ShowBottomSheet: SearchClassAction
    data object HideBottomSheet: SearchClassAction

    // Search & Refresh
    data class UpdateSearchQuery(val query: String) : SearchClassAction
    data object Search : SearchClassAction
    data object Refresh : SearchClassAction

    // Filters
    data class SelectTeacher(val teacher: Teacher?) : SearchClassAction
    data class SelectRoom(val room: Room?) : SearchClassAction
    data class SelectBatch(val batch: Batch?) : SearchClassAction
    data class SelectCourse(val course: Course?) : SearchClassAction
    data class SelectDayOfWeek(val dayOfWeek: DayOfWeek?) : SearchClassAction
    data class SelectClassType(val classType: ClassType?) : SearchClassAction
    data class ToggleIsExtraClass(val isExtraClass: Boolean?) : SearchClassAction

    data object ResetFilters : SearchClassAction
    data object ApplyFilters : SearchClassAction

    // Class Selection
    data class ClassCardClick(val classId: String) : SearchClassAction

    // Pagination
    data object LoadMoreClasses : SearchClassAction

    data object ClickFloatingActionButton : SearchClassAction
}

