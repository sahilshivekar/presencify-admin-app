package edu.watumull.presencify.feature.schedule.dashboard

sealed interface ScheduleDashboardAction {
    data object ClickRoom : ScheduleDashboardAction
    data object ClickClasses : ScheduleDashboardAction
}

