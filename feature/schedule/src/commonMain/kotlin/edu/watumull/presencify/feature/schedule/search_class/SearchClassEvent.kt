package edu.watumull.presencify.feature.schedule.search_class

sealed interface SearchClassEvent {
    data object NavigateBack : SearchClassEvent
    data class NavigateToClassDetails(val classId: String) : SearchClassEvent
    data object NavigateToAddEditClass : SearchClassEvent
}

