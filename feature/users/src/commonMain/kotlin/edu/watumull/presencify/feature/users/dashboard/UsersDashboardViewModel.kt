package edu.watumull.presencify.feature.users.dashboard

import edu.watumull.presencify.core.presentation.utils.BaseViewModel

class UsersDashboardViewModel : BaseViewModel<UsersDashboardState, UsersDashboardEvent, UsersDashboardAction>(
    initialState = UsersDashboardState()
) {
    override fun handleAction(action: UsersDashboardAction) {
        when (action) {
            UsersDashboardAction.ClickStudents -> sendEvent(UsersDashboardEvent.NavigateToSearchStudents)
            UsersDashboardAction.ClickTeachers -> sendEvent(UsersDashboardEvent.NavigateToSearchTeachers)

            UsersDashboardAction.ClickAssignUnassignSemester -> sendEvent(UsersDashboardEvent.NavigateToAssignUnassignSemester)
            UsersDashboardAction.ClickAssignUnassignDivision -> sendEvent(UsersDashboardEvent.NavigateToAssignUnassignDivision)
            UsersDashboardAction.ClickAssignUnassignBatch -> sendEvent(UsersDashboardEvent.NavigateToAssignUnassignBatch)

            UsersDashboardAction.ClickModifyDivision -> sendEvent(UsersDashboardEvent.NavigateToModifyDivision)
            UsersDashboardAction.ClickModifyBatch -> sendEvent(UsersDashboardEvent.NavigateToModifyBatch)

            UsersDashboardAction.ClickAddToDropout -> sendEvent(UsersDashboardEvent.NavigateToAddToDropout)
            UsersDashboardAction.ClickRemoveFromDropout -> sendEvent(UsersDashboardEvent.NavigateToRemoveFromDropout)
        }
    }
}
