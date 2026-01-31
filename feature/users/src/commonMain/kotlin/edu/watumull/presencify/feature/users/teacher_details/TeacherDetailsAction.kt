package edu.watumull.presencify.feature.users.teacher_details

sealed interface TeacherDetailsAction {
    data object BackButtonClick : TeacherDetailsAction
    data object DismissDialog : TeacherDetailsAction
    data object ToggleImageDialog : TeacherDetailsAction
    data object RemoveImageClick : TeacherDetailsAction
    data object UpdateTeacherImageClick : TeacherDetailsAction
    data class TeacherNewImageUploaded(val imageBytes: ByteArray?) : TeacherDetailsAction
    data object RemoveTeacherClick : TeacherDetailsAction
    data object ConfirmRemoveTeacher : TeacherDetailsAction
    data object EditTeacherDetailsClick : TeacherDetailsAction
    data object AssignUnassignCoursesClick : TeacherDetailsAction
}
