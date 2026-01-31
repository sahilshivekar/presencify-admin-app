package edu.watumull.presencify.feature.users.student_details

sealed interface StudentDetailsAction {
    data object BackButtonClick : StudentDetailsAction
    data object DismissDialog : StudentDetailsAction
    data object ToggleImageDialog : StudentDetailsAction
    data object RemoveImageClick : StudentDetailsAction
    data object UpdateStudentImageClick : StudentDetailsAction
    data class StudentNewImageUploaded(val imageBytes: ByteArray?) : StudentDetailsAction
    data object RemoveStudentClick : StudentDetailsAction
    data object ConfirmRemoveStudent : StudentDetailsAction
    data object EditStudentDetailsClick : StudentDetailsAction
}

