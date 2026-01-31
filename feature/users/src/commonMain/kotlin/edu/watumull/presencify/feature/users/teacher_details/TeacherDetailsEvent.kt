package edu.watumull.presencify.feature.users.teacher_details

sealed interface TeacherDetailsEvent {
    data object NavigateBack : TeacherDetailsEvent
    data class NavigateToEditTeacher(val teacherId: String) : TeacherDetailsEvent
    data class NavigateToAssignUnassignCourses(val teacherId: String) : TeacherDetailsEvent
}
