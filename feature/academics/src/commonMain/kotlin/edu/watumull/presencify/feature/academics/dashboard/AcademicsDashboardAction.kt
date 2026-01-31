package edu.watumull.presencify.feature.academics.dashboard

sealed interface AcademicsDashboardAction {
    data object ClickBranch : AcademicsDashboardAction
    data object ClickScheme : AcademicsDashboardAction
    data object ClickCourse : AcademicsDashboardAction
    data object ClickLinkUnlinkCourse : AcademicsDashboardAction
    data object ClickUniversity : AcademicsDashboardAction
    data object ClickSemester : AcademicsDashboardAction
    data object ClickDivision : AcademicsDashboardAction
    data object ClickBatch : AcademicsDashboardAction
}