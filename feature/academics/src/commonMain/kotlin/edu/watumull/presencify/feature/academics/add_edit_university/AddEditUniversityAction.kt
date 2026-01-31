package edu.watumull.presencify.feature.academics.add_edit_university

sealed interface AddEditUniversityAction {
    data object BackButtonClick : AddEditUniversityAction
    data object DismissDialog : AddEditUniversityAction
    data object SubmitClick : AddEditUniversityAction

    data class UpdateName(val name: String) : AddEditUniversityAction
    data class UpdateAbbreviation(val abbreviation: String) : AddEditUniversityAction
}
