package edu.watumull.presencify.feature.academics.division_details

sealed interface DivisionDetailsEvent {
    data object NavigateBack : DivisionDetailsEvent
    data class NavigateToEditDivision(val divisionId: String) : DivisionDetailsEvent
}

