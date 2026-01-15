package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.attendance.navigation.AttendanceRoutes

/**
 * Navigate to Attendance Dashboard screen
 */
fun NavController.navigateToAttendanceDashboard() {
    navigate(AttendanceRoutes.AttendanceDashboard)
}

/**
 * Navigate to Create Attendance screen
 */
fun NavController.navigateToCreateAttendance() {
    navigate(AttendanceRoutes.CreateAttendance)
}

/**
 * Navigate to Edit Attendance Details screen
 *
 * @param attendanceId The ID of the attendance to edit
 */
fun NavController.navigateToEditAttendanceDetails(attendanceId: String) {
    navigate(AttendanceRoutes.EditAttendanceDetails(attendanceId = attendanceId))
}

/**
 * Navigate to Edit Students Attendance screen
 *
 * @param attendanceId The ID of the attendance to edit students for
 */
fun NavController.navigateToEditStudentsAttendance(attendanceId: String) {
    navigate(AttendanceRoutes.EditStudentsAttendance(attendanceId = attendanceId))
}

/**
 * Navigate to Attendance Details screen
 *
 * @param attendanceId The ID of the attendance to view
 */
fun NavController.navigateToAttendanceDetails(attendanceId: String) {
    navigate(AttendanceRoutes.AttendanceDetails(attendanceId = attendanceId))
}

