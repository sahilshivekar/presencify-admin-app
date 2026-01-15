package edu.watumull.presencify.feature.users.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

enum class SearchStudentIntention {

    ADD_STUDENT_TO_SEMESTER, REMOVE_STUDENT_FROM_SEMESTER,

    ADD_STUDENT_TO_DIVISION, MODIFY_STUDENT_DIVISION, REMOVE_STUDENT_FROM_DIVISION,

    ADD_STUDENT_TO_BATCH, MODIFY_STUDENT_BATCH, REMOVE_STUDENT_FROM_BATCH,

    SELECT_STUDENT, DEFAULT

}

enum class SearchTeacherIntention {

    SELECT_TEACHER, DEFAULT

}

sealed interface UsersRoutes : NavRoute {

    @Serializable
    data object UsersDashboard : UsersRoutes

    @Serializable
    data class AddEditStudent(val studentId: String? = null) : UsersRoutes

    @Serializable
    data class SearchStudent(

        val searchQuery: String? = null,

        val branchIds: List<String>? = null,

        val semesterNumbers: List<Int>? = null, //SemesterNumber

        val academicStartYearOfSemester: Int? = null,

        val academicEndYearOfSemester: Int? = null,

        val semesterId: String? = null,

        val batchId: String? = null,

        val schemeId: String? = null,

        val divisionId: String? = null,

        val dropoutAcademicStartYear: Int? = null,

        val dropoutAcademicEndYear: Int? = null,

        val admissionTypes: List<String>? = null, // AdmissionType

        val admissionYear: Int? = null,

        val currentBatch: Boolean? = null,

        val currentDivision: Boolean? = null,

        val currentSemester: Boolean? = null,

        val divisionCode: String? = null,

        val batchCode: String? = null,

        val getAll: Boolean? = null,

        val intention: String = SearchStudentIntention.DEFAULT.name,

    ) : UsersRoutes

    @Serializable
    data object AddStudentToSemester : UsersRoutes

    @Serializable
    data object RemoveStudentFromSemester : UsersRoutes

    @Serializable
    data object AddStudentToDivision : UsersRoutes

    @Serializable
    data object ModifyStudentDivision : UsersRoutes

    @Serializable
    data object RemoveStudentFromDivision : UsersRoutes

    @Serializable
    data object AddStudentToBatch : UsersRoutes

    @Serializable
    data object ModifyStudentBatch : UsersRoutes

    @Serializable
    data object RemoveStudentFromBatch : UsersRoutes

    @Serializable
    data object AddStudentToDropout : UsersRoutes

    @Serializable
    data object RemoveStudentFromDropout : UsersRoutes

    @Serializable
    data class StudentDetails(val studentId: String) : UsersRoutes

    @Serializable
    data class AddEditTeacher(val teacherId: String? = null) : UsersRoutes

    @Serializable
    data class SearchTeacher(

        val semesterNumbers: List<Int>? = null, //SemesterNumber

        val academicStartYearOfSemester: Int? = null,

        val academicEndYearOfSemester: Int? = null,

        val intention: String = SearchTeacherIntention.DEFAULT.name, //SearchTeacherIntention

    ) : UsersRoutes

    @Serializable
    data object AssignSubjectToTeacher : UsersRoutes

    @Serializable
    data object UnassignSubjectToTeacher : UsersRoutes

    @Serializable
    data class StaffDetails(val teacherId: String) : UsersRoutes

}
