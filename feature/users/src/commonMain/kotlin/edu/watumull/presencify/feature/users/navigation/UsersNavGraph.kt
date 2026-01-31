package edu.watumull.presencify.feature.users.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions
import edu.watumull.presencify.feature.users.add_edit_student.AddEditStudentRoot
import edu.watumull.presencify.feature.users.add_edit_teacher.AddEditTeacherRoot
import edu.watumull.presencify.feature.users.dashboard.UsersDashboardRoot
import edu.watumull.presencify.feature.users.search_student.SearchStudentRoot
import edu.watumull.presencify.feature.users.search_teacher.SearchTeacherRoot
import edu.watumull.presencify.feature.users.student_details.StudentDetailsRoot
import edu.watumull.presencify.feature.users.teacher_details.TeacherDetailsRoot

fun NavGraphBuilder.usersDashboard(
    onNavigateToSearchStudents: () -> Unit,
    onNavigateToSearchTeachers: () -> Unit,
    onNavigateToAssignUnassignSemester: () -> Unit,
    onNavigateToAssignUnassignDivision: () -> Unit,
    onNavigateToAssignUnassignBatch: () -> Unit,
    onNavigateToModifyDivision: () -> Unit,
    onNavigateToModifyBatch: () -> Unit,
    onNavigateToAddToDropout: () -> Unit,
    onNavigateToRemoveFromDropout: () -> Unit,
) {
    composableWithSlideTransitions<UsersRoutes.UsersDashboard> {
        UsersDashboardRoot(
            onNavigateToSearchStudents = onNavigateToSearchStudents,
            onNavigateToSearchTeachers = onNavigateToSearchTeachers,
            onNavigateToAssignUnassignSemester = onNavigateToAssignUnassignSemester,
            onNavigateToAssignUnassignDivision = onNavigateToAssignUnassignDivision,
            onNavigateToAssignUnassignBatch = onNavigateToAssignUnassignBatch,
            onNavigateToModifyDivision = onNavigateToModifyDivision,
            onNavigateToModifyBatch = onNavigateToModifyBatch,
            onNavigateToAddToDropout = onNavigateToAddToDropout,
            onNavigateToRemoveFromDropout = onNavigateToRemoveFromDropout
        )
    }
}

fun NavGraphBuilder.usersNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateToStudentDetails: (String) -> Unit,
    onNavigateToAddEditStudent: (studentId: String?) -> Unit,
    onNavigateToTeacherDetails: (String) -> Unit,
    onNavigateToAddEditTeacher: (String?) -> Unit,
    onNavigateToAssignUnassignCourses: (String) -> Unit
) {

    composableWithSlideTransitions<UsersRoutes.AddEditStudent> {
        AddEditStudentRoot(
            onNavigateBack = onNavigateBack
        )
    }
    composableWithSlideTransitions<UsersRoutes.SearchStudent> {
        SearchStudentRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToStudentDetails = onNavigateToStudentDetails,
            onNavigateToAddEditStudent = onNavigateToAddEditStudent
        )
    }
    composableWithSlideTransitions<UsersRoutes.AssignUnassignStudentToSemester> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AssignUnassignStudentToDivision> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.ModifyStudentDivision> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AssignUnassignStudentToBatch> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.ModifyStudentBatch> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AddStudentToDropout> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.RemoveStudentFromDropout> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.StudentDetails> {
        StudentDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditStudent = onNavigateToAddEditStudent
        )
    }
    composableWithSlideTransitions<UsersRoutes.AddEditTeacher> {
        AddEditTeacherRoot(
            onNavigateBack = onNavigateBack
        )
    }
    composableWithSlideTransitions<UsersRoutes.SearchTeacher> {
        SearchTeacherRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToTeacherDetails = onNavigateToTeacherDetails,
            onNavigateToAddEditTeacher = onNavigateToAddEditTeacher
        )
    }
    composableWithSlideTransitions<UsersRoutes.AssignCourseToTeacher> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.UnassignCourseToTeacher> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.TeacherDetails> {
        TeacherDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditTeacher = onNavigateToAddEditTeacher,
            onNavigateToAssignUnassignCourses = onNavigateToAssignUnassignCourses
        )
    }
}
