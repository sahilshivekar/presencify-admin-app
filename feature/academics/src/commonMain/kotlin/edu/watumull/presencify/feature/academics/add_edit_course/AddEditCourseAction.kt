package edu.watumull.presencify.feature.academics.add_edit_course

sealed interface AddEditCourseAction {
    data object BackButtonClick : AddEditCourseAction
    data object DismissDialog : AddEditCourseAction
    data object SubmitClick : AddEditCourseAction

    data class UpdateCode(val code: String) : AddEditCourseAction
    data class UpdateName(val name: String) : AddEditCourseAction
    data class UpdateOptionalCourse(val optionalCourse: String) : AddEditCourseAction
    data class UpdateSelectedScheme(val schemeId: String) : AddEditCourseAction
    data class ChangeSchemeDropDownVisibility(val isVisible: Boolean) : AddEditCourseAction
}

