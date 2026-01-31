package edu.watumull.presencify.feature.academics.scheme_details

sealed interface SchemeDetailsEvent {
    data object NavigateBack : SchemeDetailsEvent
    data class NavigateToEditScheme(val schemeId: String) : SchemeDetailsEvent
}

