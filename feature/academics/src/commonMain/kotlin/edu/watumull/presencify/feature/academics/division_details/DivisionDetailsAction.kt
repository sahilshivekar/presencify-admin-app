package edu.watumull.presencify.feature.academics.division_details

sealed interface DivisionDetailsAction {
    data object BackButtonClick : DivisionDetailsAction
    data object DismissDialog : DivisionDetailsAction
    data object RemoveDivisionClick : DivisionDetailsAction
    data object EditDivisionClick : DivisionDetailsAction
}

