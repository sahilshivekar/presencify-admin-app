package edu.watumull.presencify.feature.users.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

enum class SearchStudentIntention {

    ASSIGN_UNASSIGN_STUDENT_TO_SEMESTER,

    ASSIGN_UNASSIGN_STUDENT_TO_DIVISION, MODIFY_STUDENT_DIVISION,

    ASSIGN_UNASSIGN_STUDENT_TO_BATCH, MODIFY_STUDENT_BATCH,

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
    data object AssignUnassignStudentToSemester : UsersRoutes

    @Serializable
    data object AssignUnassignStudentToDivision : UsersRoutes

    @Serializable
    data object ModifyStudentDivision : UsersRoutes

    @Serializable
    data object AssignUnassignStudentToBatch : UsersRoutes

    @Serializable
    data object ModifyStudentBatch : UsersRoutes


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

        val searchQuery: String? = null,

        val courseId: String? = null,

        val getAll: Boolean? = null,

        val intention: String = SearchTeacherIntention.DEFAULT.name, //SearchTeacherIntention

    ) : UsersRoutes

    @Serializable
    data object AssignCourseToTeacher : UsersRoutes

    @Serializable
    data object UnassignCourseToTeacher : UsersRoutes

    @Serializable
    data class TeacherDetails(val teacherId: String) : UsersRoutes

}
