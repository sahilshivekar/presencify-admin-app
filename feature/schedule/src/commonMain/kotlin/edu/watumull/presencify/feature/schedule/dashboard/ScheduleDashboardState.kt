package edu.watumull.presencify.feature.schedule.dashboard

data class ScheduleDashboardState(
    val viewState: ViewState = ViewState.Content
) {
    sealed interface ViewState {
        data object Loading : ViewState
        data object Content : ViewState
    }
}

