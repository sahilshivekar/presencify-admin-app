package edu.watumull.presencify.feature.academics.semester_details

sealed interface SemesterDetailsEvent {
    data object NavigateBack : SemesterDetailsEvent
    data class NavigateToEditSemester(val semesterId: String) : SemesterDetailsEvent
}

