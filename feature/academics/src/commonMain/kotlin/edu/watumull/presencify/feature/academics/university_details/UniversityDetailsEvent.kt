package edu.watumull.presencify.feature.academics.university_details

sealed interface UniversityDetailsEvent {
    data object NavigateBack : UniversityDetailsEvent
    data object NavigateToAddUniversity : UniversityDetailsEvent
    data class NavigateToEditUniversity(val universityId: String) : UniversityDetailsEvent
}
