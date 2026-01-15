package edu.watumull.presencify.feature.onboarding.select_role

import edu.watumull.presencify.core.presentation.utils.BaseViewModel

class SelectRoleViewModel :
    BaseViewModel<SelectRoleState, SelectRoleEvent, SelectRoleAction>(
        initialState = SelectRoleState(viewState = SelectRoleState.ViewState.Content)
    ) {

    override fun handleAction(action: SelectRoleAction) {
        when (action) {
            is SelectRoleAction.DismissDialog -> {
                updateState { it.copy(dialogState = null) }
            }
            SelectRoleAction.OnAdminSelected -> {
                sendEvent(SelectRoleEvent.NavigateToAdminLogin)
            }
            SelectRoleAction.OnStudentSelected -> {
                sendEvent(SelectRoleEvent.NavigateToStudentLogin)
            }
            SelectRoleAction.OnTeacherSelected -> {
                sendEvent(SelectRoleEvent.NavigateToTeacherLogin)
            }
        }
    }
}
