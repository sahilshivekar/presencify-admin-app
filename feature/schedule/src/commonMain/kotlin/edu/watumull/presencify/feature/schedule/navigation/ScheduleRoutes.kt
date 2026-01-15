package edu.watumull.presencify.feature.schedule.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

sealed interface ScheduleRoutes : NavRoute {

    @Serializable
    data object ScheduleDashboard : ScheduleRoutes

    @Serializable
    data class AddEditClass(val classId: String? = null) : ScheduleRoutes

    @Serializable
    data object SearchClass : ScheduleRoutes

    @Serializable
    data class ClassDetails(val classId: String) : ScheduleRoutes

    @Serializable
    data class AddEditRoom(val roomId: String? = null) : ScheduleRoutes

    @Serializable
    data object SearchRoom : ScheduleRoutes

    @Serializable
    data class RoomDetails(val roomId: String) : ScheduleRoutes

    @Serializable
    data class AddEditTimetable(val timetableId: String? = null) : ScheduleRoutes

    @Serializable
    data object SearchTimetable : ScheduleRoutes

    @Serializable
    data class TimetableDetails(val timetableId: String) : ScheduleRoutes

    @Serializable
    data class StudentSchedule(val studentId: String? = null) : ScheduleRoutes

    @Serializable
    data class TeacherSchedule(val teacherId: String? = null) : ScheduleRoutes

}
