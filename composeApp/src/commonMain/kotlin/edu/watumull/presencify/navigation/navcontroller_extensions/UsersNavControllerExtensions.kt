package edu.watumull.presencify.navigation.navcontroller_extensions

import androidx.navigation.NavController
import edu.watumull.presencify.feature.users.navigation.SearchStudentIntention
import edu.watumull.presencify.feature.users.navigation.SearchTeacherIntention
import edu.watumull.presencify.feature.users.navigation.UsersRoutes

/**
 * Navigate to Users Dashboard screen
 */
fun NavController.navigateToUsersDashboard() {
    navigate(UsersRoutes.UsersDashboard)
}

/**
 * Navigate to Add/Edit Student screen
 *
 * @param studentId The ID of the student to edit, null for creating a new student
 */
fun NavController.navigateToAddEditStudent(studentId: String? = null) {
    navigate(UsersRoutes.AddEditStudent(studentId = studentId))
}

/**
 * Navigate to Search Student screen
 *
 * @param searchQuery The search query string
 * @param branchIds List of branch IDs to filter by
 * @param semesterNumbers List of semester numbers to filter by
 * @param academicStartYearOfSemester Academic start year of semester
 * @param academicEndYearOfSemester Academic end year of semester
 * @param semesterId The ID of the semester to filter by
 * @param batchId The ID of the batch to filter by
 * @param schemeId The ID of the scheme to filter by
 * @param divisionId The ID of the division to filter by
 * @param dropoutAcademicStartYear Dropout academic start year
 * @param dropoutAcademicEndYear Dropout academic end year
 * @param admissionTypes List of admission types to filter by
 * @param admissionYear Admission year to filter by
 * @param currentBatch Filter by current batch
 * @param currentDivision Filter by current division
 * @param currentSemester Filter by current semester
 * @param divisionCode Division code to filter by
 * @param batchCode Batch code to filter by
 * @param getAll Get all students flag
 * @param intention The intention for search (see SearchStudentIntention)
 */
fun NavController.navigateToSearchStudent(
    searchQuery: String? = null,
    branchIds: List<String>? = null,
    semesterNumbers: List<Int>? = null,
    academicStartYearOfSemester: Int? = null,
    academicEndYearOfSemester: Int? = null,
    semesterId: String? = null,
    batchId: String? = null,
    schemeId: String? = null,
    divisionId: String? = null,
    dropoutAcademicStartYear: Int? = null,
    dropoutAcademicEndYear: Int? = null,
    admissionTypes: List<String>? = null,
    admissionYear: Int? = null,
    currentBatch: Boolean? = null,
    currentDivision: Boolean? = null,
    currentSemester: Boolean? = null,
    divisionCode: String? = null,
    batchCode: String? = null,
    getAll: Boolean? = null,
    intention: String = SearchStudentIntention.DEFAULT.name,
) {
    navigate(
        UsersRoutes.SearchStudent(
            searchQuery = searchQuery,
            branchIds = branchIds,
            semesterNumbers = semesterNumbers,
            academicStartYearOfSemester = academicStartYearOfSemester,
            academicEndYearOfSemester = academicEndYearOfSemester,
            semesterId = semesterId,
            batchId = batchId,
            schemeId = schemeId,
            divisionId = divisionId,
            dropoutAcademicStartYear = dropoutAcademicStartYear,
            dropoutAcademicEndYear = dropoutAcademicEndYear,
            admissionTypes = admissionTypes,
            admissionYear = admissionYear,
            currentBatch = currentBatch,
            currentDivision = currentDivision,
            currentSemester = currentSemester,
            divisionCode = divisionCode,
            batchCode = batchCode,
            getAll = getAll,
            intention = intention
        )
    )
}

/**
 * Navigate to Assign/Unassign Student to Semester screen
 */
fun NavController.navigateToAssignUnassignStudentToSemester() {
    navigate(UsersRoutes.AssignUnassignStudentToSemester)
}

/**
 * Navigate to Assign/Unassign Student to Division screen
 */
fun NavController.navigateToAssignUnassignStudentToDivision() {
    navigate(UsersRoutes.AssignUnassignStudentToDivision)
}

/**
 * Navigate to Modify Student Division screen
 */
fun NavController.navigateToModifyStudentDivision() {
    navigate(UsersRoutes.ModifyStudentDivision)
}

/**
 * Navigate to Assign/Unassign Student to Batch screen
 */
fun NavController.navigateToAssignUnassignStudentToBatch() {
    navigate(UsersRoutes.AssignUnassignStudentToBatch)
}

/**
 * Navigate to Modify Student Batch screen
 */
fun NavController.navigateToModifyStudentBatch() {
    navigate(UsersRoutes.ModifyStudentBatch)
}


/**
 * Navigate to Add Student to Dropout screen
 */
fun NavController.navigateToAddStudentToDropout() {
    navigate(UsersRoutes.AddStudentToDropout)
}

/**
 * Navigate to Remove Student from Dropout screen
 */
fun NavController.navigateToRemoveStudentFromDropout() {
    navigate(UsersRoutes.RemoveStudentFromDropout)
}

/**
 * Navigate to Student Details screen
 *
 * @param studentId The ID of the student to view
 */
fun NavController.navigateToStudentDetails(studentId: String) {
    navigate(UsersRoutes.StudentDetails(studentId = studentId))
}

/**
 * Navigate to Add/Edit Teacher screen
 *
 * @param teacherId The ID of the teacher to edit, null for creating a new teacher
 */
fun NavController.navigateToAddEditTeacher(teacherId: String? = null) {
    navigate(UsersRoutes.AddEditTeacher(teacherId = teacherId))
}

/**
 * Navigate to Search Teacher screen
 *
 * @param searchQuery The search query string
 * @param courseId The ID of the course to filter teachers by
 * @param getAll Get all teachers flag
 * @param intention The intention for search (see SearchTeacherIntention)
 */
fun NavController.navigateToSearchTeacher(
    searchQuery: String? = null,
    courseId: String? = null,
    getAll: Boolean? = null,
    intention: String = SearchTeacherIntention.DEFAULT.name,
) {
    navigate(
        UsersRoutes.SearchTeacher(
            searchQuery = searchQuery,
            courseId = courseId,
            getAll = getAll,
            intention = intention
        )
    )
}

/**
 * Navigate to Assign Course to Teacher screen
 */
fun NavController.navigateToAssignCourseToTeacher() {
    navigate(UsersRoutes.AssignCourseToTeacher)
}

/**
 * Navigate to Unassign Course to Teacher screen
 */
fun NavController.navigateToUnassignCourseToTeacher() {
    navigate(UsersRoutes.UnassignCourseToTeacher)
}

/**
 * Navigate to Staff Details screen
 *
 * @param teacherId The ID of the teacher to view
 */
fun NavController.navigateToTeacherDetails(teacherId: String) {
    navigate(UsersRoutes.TeacherDetails(teacherId = teacherId))
}

