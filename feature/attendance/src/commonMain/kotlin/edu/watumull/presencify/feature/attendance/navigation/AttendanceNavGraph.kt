package edu.watumull.presencify.feature.attendance.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.attendanceDashboard() {
    composableWithSlideTransitions<AttendanceRoutes.AttendanceDashboard> {
        // TODO: Add screen content
    }
}

fun NavGraphBuilder.attendanceNavGraph() {

    composableWithSlideTransitions<AttendanceRoutes.CreateAttendance> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AttendanceRoutes.EditAttendanceDetails> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AttendanceRoutes.EditStudentsAttendance> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<AttendanceRoutes.AttendanceDetails> {
        // TODO: Add screen content
    }
}

