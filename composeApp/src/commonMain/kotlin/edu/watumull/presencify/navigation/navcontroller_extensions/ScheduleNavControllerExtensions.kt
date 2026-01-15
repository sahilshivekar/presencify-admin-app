package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.schedule.navigation.ScheduleRoutes

/**
 * Navigate to Schedule Dashboard screen
 */
fun NavController.navigateToScheduleDashboard() {
    navigate(ScheduleRoutes.ScheduleDashboard)
}

/**
 * Navigate to Add/Edit Class screen
 *
 * @param classId The ID of the class to edit, null for creating a new class
 */
fun NavController.navigateToAddEditClass(classId: String? = null) {
    navigate(ScheduleRoutes.AddEditClass(classId = classId))
}

/**
 * Navigate to Search Class screen
 */
fun NavController.navigateToSearchClass() {
    navigate(ScheduleRoutes.SearchClass)
}

/**
 * Navigate to Class Details screen
 *
 * @param classId The ID of the class to view
 */
fun NavController.navigateToClassDetails(classId: String) {
    navigate(ScheduleRoutes.ClassDetails(classId = classId))
}

/**
 * Navigate to Add/Edit Room screen
 *
 * @param roomId The ID of the room to edit, null for creating a new room
 */
fun NavController.navigateToAddEditRoom(roomId: String? = null) {
    navigate(ScheduleRoutes.AddEditRoom(roomId = roomId))
}

/**
 * Navigate to Search Room screen
 */
fun NavController.navigateToSearchRoom() {
    navigate(ScheduleRoutes.SearchRoom)
}

/**
 * Navigate to Room Details screen
 *
 * @param roomId The ID of the room to view
 */
fun NavController.navigateToRoomDetails(roomId: String) {
    navigate(ScheduleRoutes.RoomDetails(roomId = roomId))
}

/**
 * Navigate to Add/Edit Timetable screen
 *
 * @param timetableId The ID of the timetable to edit, null for creating a new timetable
 */
fun NavController.navigateToAddEditTimetable(timetableId: String? = null) {
    navigate(ScheduleRoutes.AddEditTimetable(timetableId = timetableId))
}

/**
 * Navigate to Search Timetable screen
 */
fun NavController.navigateToSearchTimetable() {
    navigate(ScheduleRoutes.SearchTimetable)
}

/**
 * Navigate to Timetable Details screen
 *
 * @param timetableId The ID of the timetable to view
 */
fun NavController.navigateToTimetableDetails(timetableId: String) {
    navigate(ScheduleRoutes.TimetableDetails(timetableId = timetableId))
}

/**
 * Navigate to Student Schedule screen
 *
 * @param studentId The ID of the student to view schedule for, null for current user's schedule
 */
fun NavController.navigateToStudentSchedule(studentId: String? = null) {
    navigate(ScheduleRoutes.StudentSchedule(studentId = studentId))
}

/**
 * Navigate to Teacher Schedule screen
 *
 * @param teacherId The ID of the teacher to view schedule for, null for current user's schedule
 */
fun NavController.navigateToTeacherSchedule(teacherId: String? = null) {
    navigate(ScheduleRoutes.TeacherSchedule(teacherId = teacherId))
}

