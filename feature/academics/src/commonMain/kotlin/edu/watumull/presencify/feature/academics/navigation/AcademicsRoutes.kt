package edu.watumull.presencify.feature.academics.navigation

import edu.watumull.presencify.core.presentation.navigation.NavRoute
import kotlinx.serialization.Serializable

enum class SearchCourseIntention {

    LINK_COURSE_TO_SEMESTER_NUMBER_BRANCH, UNLINK_COURSE_TO_SEMESTER_NUMBER_BRANCH,

    ADD_COURSE_TO_SEMESTER_ID, REMOVE_COURSE_FROM_SEMESTER_ID,

    DEFAULT

}

sealed interface AcademicsRoutes : NavRoute {

    @Serializable
    data object AcademicsDashboard : AcademicsRoutes

    @Serializable
    data class AddEditBranch(val branchId: String? = null) : AcademicsRoutes

    @Serializable
    data object SearchBranch : AcademicsRoutes

    @Serializable
    data class BranchDetails(val branchId: String) : AcademicsRoutes

    @Serializable
    data class AddEditScheme(val schemeId: String? = null) : AcademicsRoutes

    @Serializable
    data object SearchScheme : AcademicsRoutes

    @Serializable
    data class SchemeDetails(val schemeId: String) : AcademicsRoutes

    @Serializable
    data class AddEditUniversity(val universityId: String? = null) : AcademicsRoutes

    @Serializable
    data object SearchUniversity : AcademicsRoutes

    @Serializable
    data class UniversityDetails(val universityId: String) : AcademicsRoutes

    @Serializable
    data class AddEditCourse(val courseId: String? = null) : AcademicsRoutes

    @Serializable
    data class SearchCourse(

        val intention: String = SearchCourseIntention.DEFAULT.name, //SearchCourseIntention

        val branchId: String? = null,

        val semesterNumber: Int? = null, //SemesterNumber

        val schemeId: String? = null

    ) : AcademicsRoutes

    @Serializable
    data class CourseDetails(val courseId: String) : AcademicsRoutes

    @Serializable
    data object LinkCourseToSemester : AcademicsRoutes

    @Serializable
    data object UnlinkCourseToSemester : AcademicsRoutes

    @Serializable
    data class AddEditBatch(val batchId: String? = null) : AcademicsRoutes

    @Serializable
    data object SearchBatch : AcademicsRoutes

    @Serializable
    data class BatchDetails(val batchId: String) : AcademicsRoutes

    @Serializable
    data class AddEditDivision(val divisionId: String? = null) : AcademicsRoutes

    @Serializable
    data object SearchDivision : AcademicsRoutes

    @Serializable
    data class DivisionDetails(val divisionId: String) : AcademicsRoutes

    @Serializable
    data class AddEditSemester(val semesterId: String? = null) : AcademicsRoutes

    @Serializable
    data object SearchSemester : AcademicsRoutes

    @Serializable
    data class SemesterDetails(val semesterId: String) : AcademicsRoutes

}
