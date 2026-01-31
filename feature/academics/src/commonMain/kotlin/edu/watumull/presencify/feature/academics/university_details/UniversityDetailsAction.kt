package edu.watumull.presencify.feature.academics.university_details

sealed interface UniversityDetailsAction {
    data object BackButtonClick : UniversityDetailsAction
    data object DismissDialog : UniversityDetailsAction
    data object AddUniversityClick : UniversityDetailsAction
    data class EditUniversityClick(val universityId: String) : UniversityDetailsAction
    data class RemoveUniversityClick(val universityId: String) : UniversityDetailsAction
    data object ConfirmRemoveUniversity : UniversityDetailsAction
}
