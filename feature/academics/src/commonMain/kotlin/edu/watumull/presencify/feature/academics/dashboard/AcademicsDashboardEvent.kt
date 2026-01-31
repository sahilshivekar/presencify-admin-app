package edu.watumull.presencify.feature.academics.dashboard

sealed interface AcademicsDashboardEvent {
    data object NavigateToSearchBranch : AcademicsDashboardEvent
    data object NavigateToSearchScheme : AcademicsDashboardEvent
    data object NavigateToSearchCourse : AcademicsDashboardEvent
    data object NavigateToUniversityDetails : AcademicsDashboardEvent
    data object NavigateToSearchSemester : AcademicsDashboardEvent
    data object NavigateToSearchDivision : AcademicsDashboardEvent
    data object NavigateToSearchBatch : AcademicsDashboardEvent
}