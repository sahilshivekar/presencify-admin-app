package edu.watumull.presencify.feature.attendance.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface AttendanceRoutes : NavRoute {

    @Serializable
    data object AttendanceDashboard : AttendanceRoutes

    @Serializable
    data object CreateAttendance : AttendanceRoutes

    @Serializable
    data class EditAttendanceDetails(val attendanceId: String) : AttendanceRoutes

    @Serializable
    data class EditStudentsAttendance(val attendanceId: String) : AttendanceRoutes

    @Serializable
    data class AttendanceDetails(val attendanceId: String) : AttendanceRoutes

}
