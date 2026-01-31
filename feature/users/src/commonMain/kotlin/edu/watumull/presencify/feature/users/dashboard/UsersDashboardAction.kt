package edu.watumull.presencify.feature.users.dashboard

sealed interface UsersDashboardAction {
    data object ClickStudents : UsersDashboardAction
    data object ClickTeachers : UsersDashboardAction

    // Assign/Unassign
    data object ClickAssignUnassignSemester : UsersDashboardAction
    data object ClickAssignUnassignDivision : UsersDashboardAction
    data object ClickAssignUnassignBatch : UsersDashboardAction

    // Modify
    data object ClickModifyDivision : UsersDashboardAction
    data object ClickModifyBatch : UsersDashboardAction

    // Dropout
    data object ClickAddToDropout : UsersDashboardAction
    data object ClickRemoveFromDropout : UsersDashboardAction
}