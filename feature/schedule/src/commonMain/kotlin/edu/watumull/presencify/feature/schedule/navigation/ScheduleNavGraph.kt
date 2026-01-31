package edu.watumull.presencify.feature.schedule.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions
import edu.watumull.presencify.feature.schedule.dashboard.ScheduleDashboardRoot
import edu.watumull.presencify.feature.schedule.search_class.SearchClassRoot

fun NavGraphBuilder.scheduleDashboard(
    onNavigateToSearchRoom: () -> Unit,
    onNavigateToSearchClass: () -> Unit
) {
    composableWithSlideTransitions<ScheduleRoutes.ScheduleDashboard> {
        ScheduleDashboardRoot(
            onNavigateToSearchRoom = onNavigateToSearchRoom,
            onNavigateToSearchClass = onNavigateToSearchClass
        )
    }
}

fun NavGraphBuilder.scheduleNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateToClassDetails: (String) -> Unit,
    onNavigateToAddEditClass: (String?) -> Unit
) {
    composableWithSlideTransitions<ScheduleRoutes.AddEditClass> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.SearchClass> {
        SearchClassRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToClassDetails = onNavigateToClassDetails,
            onNavigateToAddEditClass = onNavigateToAddEditClass
        )
    }
    composableWithSlideTransitions<ScheduleRoutes.ClassDetails> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.AddEditRoom> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.SearchRoom> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.RoomDetails> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.AddEditTimetable> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.SearchTimetable> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.TimetableDetails> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.StudentSchedule> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.TeacherSchedule> {
        // TODO: Add screen content
    }
}

