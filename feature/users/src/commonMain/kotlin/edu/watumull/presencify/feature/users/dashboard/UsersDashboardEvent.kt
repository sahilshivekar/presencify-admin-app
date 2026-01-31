package edu.watumull.presencify.feature.users.dashboard

sealed interface UsersDashboardEvent {
    data object NavigateToSearchStudents : UsersDashboardEvent
    data object NavigateToSearchTeachers : UsersDashboardEvent

    data object NavigateToAssignUnassignSemester : UsersDashboardEvent
    data object NavigateToAssignUnassignDivision : UsersDashboardEvent
    data object NavigateToAssignUnassignBatch : UsersDashboardEvent

    data object NavigateToModifyDivision : UsersDashboardEvent
    data object NavigateToModifyBatch : UsersDashboardEvent

    data object NavigateToAddToDropout : UsersDashboardEvent
    data object NavigateToRemoveFromDropout : UsersDashboardEvent
}