package edu.watumull.presencify.feature.onboarding.select_role

sealed interface SelectRoleEvent {
    data object NavigateToStudentLogin : SelectRoleEvent
    data object NavigateToTeacherLogin : SelectRoleEvent
    data object NavigateToAdminLogin : SelectRoleEvent
}