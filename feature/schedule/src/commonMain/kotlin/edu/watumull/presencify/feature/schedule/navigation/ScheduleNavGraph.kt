package edu.watumull.presencify.feature.schedule.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.scheduleDashboard() {
    composableWithSlideTransitions<ScheduleRoutes.ScheduleDashboard> {
        // TODO: Add screen content
    }
}

fun NavGraphBuilder.scheduleNavGraph() {
    composableWithSlideTransitions<ScheduleRoutes.AddEditClass> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<ScheduleRoutes.SearchClass> {
        // TODO: Add screen content
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

