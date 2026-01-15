package edu.watumull.presencify.feature.users.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions

fun NavGraphBuilder.usersDashboard() {
    composableWithSlideTransitions<UsersRoutes.UsersDashboard> {
        // TODO: Add screen content
    }
}

fun NavGraphBuilder.usersNavGraph() {

    composableWithSlideTransitions<UsersRoutes.AddEditStudent> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.SearchStudent> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AddStudentToSemester> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.RemoveStudentFromSemester> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AddStudentToDivision> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.ModifyStudentDivision> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.RemoveStudentFromDivision> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AddStudentToBatch> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.ModifyStudentBatch> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.RemoveStudentFromBatch> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AddStudentToDropout> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.RemoveStudentFromDropout> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.StudentDetails> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AddEditTeacher> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.SearchTeacher> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.AssignSubjectToTeacher> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.UnassignSubjectToTeacher> {
        // TODO: Add screen content
    }
    composableWithSlideTransitions<UsersRoutes.StaffDetails> {
        // TODO: Add screen content
    }
}
