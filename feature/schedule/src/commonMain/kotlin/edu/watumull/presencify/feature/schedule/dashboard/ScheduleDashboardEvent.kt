package edu.watumull.presencify.feature.schedule.dashboard

sealed interface ScheduleDashboardEvent {
    data object NavigateToRoom : ScheduleDashboardEvent
    data object NavigateToClasses : ScheduleDashboardEvent
}

