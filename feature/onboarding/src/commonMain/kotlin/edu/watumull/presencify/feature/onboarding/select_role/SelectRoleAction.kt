package edu.watumull.presencify.feature.onboarding.select_role

sealed interface SelectRoleAction {
    data object DismissDialog : SelectRoleAction

    data object OnStudentSelected : SelectRoleAction
    data object OnTeacherSelected : SelectRoleAction
    data object OnAdminSelected : SelectRoleAction
}

enum class DialogIntention {
    GENERIC,
}