package edu.watumull.presencify.feature.schedule.dashboard

import edu.watumull.presencify.core.presentation.utils.BaseViewModel

class ScheduleDashboardViewModel : BaseViewModel<ScheduleDashboardState, ScheduleDashboardEvent, ScheduleDashboardAction>(
    initialState = ScheduleDashboardState()
) {
    override fun handleAction(action: ScheduleDashboardAction) {
        when (action) {
            ScheduleDashboardAction.ClickRoom -> sendEvent(ScheduleDashboardEvent.NavigateToRoom)
            ScheduleDashboardAction.ClickClasses -> sendEvent(ScheduleDashboardEvent.NavigateToClasses)
        }
    }
}

