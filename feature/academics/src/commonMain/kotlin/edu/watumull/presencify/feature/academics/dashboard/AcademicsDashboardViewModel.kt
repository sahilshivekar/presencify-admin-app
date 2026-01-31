package edu.watumull.presencify.feature.academics.dashboard

import edu.watumull.presencify.core.presentation.utils.BaseViewModel


class AcademicsDashboardViewModel : BaseViewModel<AcademicsDashboardState, AcademicsDashboardEvent, AcademicsDashboardAction>(
    initialState = AcademicsDashboardState()
) {
    override fun handleAction(action: AcademicsDashboardAction) {
        when (action) {
            AcademicsDashboardAction.ClickBranch -> sendEvent(AcademicsDashboardEvent.NavigateToSearchBranch)
            AcademicsDashboardAction.ClickScheme -> sendEvent(AcademicsDashboardEvent.NavigateToSearchScheme)
            AcademicsDashboardAction.ClickCourse -> sendEvent(AcademicsDashboardEvent.NavigateToSearchCourse)
            AcademicsDashboardAction.ClickUniversity -> sendEvent(AcademicsDashboardEvent.NavigateToUniversityDetails)
            AcademicsDashboardAction.ClickSemester -> sendEvent(AcademicsDashboardEvent.NavigateToSearchSemester)
            AcademicsDashboardAction.ClickDivision -> sendEvent(AcademicsDashboardEvent.NavigateToSearchDivision)
            AcademicsDashboardAction.ClickBatch -> sendEvent(AcademicsDashboardEvent.NavigateToSearchBatch)
        }
    }
}