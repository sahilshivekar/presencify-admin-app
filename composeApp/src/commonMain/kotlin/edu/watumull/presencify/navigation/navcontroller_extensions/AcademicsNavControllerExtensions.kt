package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.academics.navigation.AcademicsRoutes
import edu.watumull.presencify.feature.academics.navigation.SearchCourseIntention

/**
 * Navigate to Academics Dashboard screen
 */
fun NavController.navigateToAcademicsDashboard() {
    navigate(AcademicsRoutes.AcademicsDashboard)
}

/**
 * Navigate to Add/Edit Branch screen
 *
 * @param branchId The ID of the branch to edit, null for creating a new branch
 */
fun NavController.navigateToAddEditBranch(branchId: String? = null) {
    navigate(AcademicsRoutes.AddEditBranch(branchId = branchId))
}

/**
 * Navigate to Search Branch screen
 */
fun NavController.navigateToSearchBranch() {
    navigate(AcademicsRoutes.SearchBranch)
}

/**
 * Navigate to Branch Details screen
 *
 * @param branchId The ID of the branch to view
 */
fun NavController.navigateToBranchDetails(branchId: String) {
    navigate(AcademicsRoutes.BranchDetails(branchId = branchId))
}

/**
 * Navigate to Add/Edit Scheme screen
 *
 * @param schemeId The ID of the scheme to edit, null for creating a new scheme
 */
fun NavController.navigateToAddEditScheme(schemeId: String? = null) {
    navigate(AcademicsRoutes.AddEditScheme(schemeId = schemeId))
}

/**
 * Navigate to Search Scheme screen
 */
fun NavController.navigateToSearchScheme() {
    navigate(AcademicsRoutes.SearchScheme)
}

/**
 * Navigate to Scheme Details screen
 *
 * @param schemeId The ID of the scheme to view
 */
fun NavController.navigateToSchemeDetails(schemeId: String) {
    navigate(AcademicsRoutes.SchemeDetails(schemeId = schemeId))
}

/**
 * Navigate to Add/Edit University screen
 *
 * @param universityId The ID of the university to edit, null for creating a new university
 */
fun NavController.navigateToAddEditUniversity(universityId: String? = null) {
    navigate(AcademicsRoutes.AddEditUniversity(universityId = universityId))
}

/**
 * Navigate to University Details screen (shows all universities)
 */
fun NavController.navigateToUniversityDetails() {
    navigate(AcademicsRoutes.UniversityDetails)
}

/**
 * Navigate to Add/Edit Course screen
 *
 * @param courseId The ID of the course to edit, null for creating a new course
 */
fun NavController.navigateToAddEditCourse(courseId: String? = null) {
    navigate(AcademicsRoutes.AddEditCourse(courseId = courseId))
}

/**
 * Navigate to Search Course screen
 *
 * @param intention The intention for search (see SearchCourseIntention)
 * @param branchId The ID of the branch to filter by
 * @param semesterNumber The semester number to filter by
 * @param schemeId The ID of the scheme to filter by
 */
fun NavController.navigateToSearchCourse(
    intention: String = SearchCourseIntention.DEFAULT.name,
    branchId: String? = null,
    semesterNumber: Int? = null,
    schemeId: String? = null,
) {
    navigate(
        AcademicsRoutes.SearchCourse(
            intention = intention,
            branchId = branchId,
            semesterNumber = semesterNumber,
            schemeId = schemeId
        )
    )
}

/**
 * Navigate to Course Details screen
 *
 * @param courseId The ID of the course to view
 */
fun NavController.navigateToCourseDetails(courseId: String) {
    navigate(AcademicsRoutes.CourseDetails(courseId = courseId))
}

/**
 * Navigate to Link Course to Semester screen
 */
fun NavController.navigateToLinkCourseToSemester() {
    navigate(AcademicsRoutes.LinkCourseToSemester)
}

/**
 * Navigate to Unlink Course to Semester screen
 */
fun NavController.navigateToUnlinkCourseToSemester() {
    navigate(AcademicsRoutes.UnlinkCourseToSemester)
}

/**
 * Navigate to Add/Edit Batch screen
 *
 * @param batchId The ID of the batch to edit, null for creating a new batch
 */
fun NavController.navigateToAddEditBatch(batchId: String? = null) {
    navigate(AcademicsRoutes.AddEditBatch(batchId = batchId))
}

/**
 * Navigate to Search Batch screen
 */
fun NavController.navigateToSearchBatch() {
    navigate(AcademicsRoutes.SearchBatch)
}

/**
 * Navigate to Batch Details screen
 *
 * @param batchId The ID of the batch to view
 */
fun NavController.navigateToBatchDetails(batchId: String) {
    navigate(AcademicsRoutes.BatchDetails(batchId = batchId))
}

/**
 * Navigate to Add/Edit Division screen
 *
 * @param divisionId The ID of the division to edit, null for creating a new division
 */
fun NavController.navigateToAddEditDivision(divisionId: String? = null) {
    navigate(AcademicsRoutes.AddEditDivision(divisionId = divisionId))
}

/**
 * Navigate to Search Division screen
 */
fun NavController.navigateToSearchDivision() {
    navigate(AcademicsRoutes.SearchDivision)
}

/**
 * Navigate to Division Details screen
 *
 * @param divisionId The ID of the division to view
 */
fun NavController.navigateToDivisionDetails(divisionId: String) {
    navigate(AcademicsRoutes.DivisionDetails(divisionId = divisionId))
}

/**
 * Navigate to Add/Edit Semester screen
 *
 * @param semesterId The ID of the semester to edit, null for creating a new semester
 */
fun NavController.navigateToAddEditSemester(semesterId: String? = null) {
    navigate(AcademicsRoutes.AddEditSemester(semesterId = semesterId))
}

/**
 * Navigate to Search Semester screen
 */
fun NavController.navigateToSearchSemester() {
    navigate(AcademicsRoutes.SearchSemester)
}

/**
 * Navigate to Semester Details screen
 *
 * @param semesterId The ID of the semester to view
 */
fun NavController.navigateToSemesterDetails(semesterId: String) {
    navigate(AcademicsRoutes.SemesterDetails(semesterId = semesterId))
}