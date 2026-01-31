package edu.watumull.presencify.feature.academics.navigation

import androidx.navigation.NavGraphBuilder
import edu.watumull.presencify.core.design.systems.components.composableWithSlideTransitions
import edu.watumull.presencify.feature.academics.add_edit_batch.AddEditBatchRoot
import edu.watumull.presencify.feature.academics.add_edit_branch.AddEditBranchRoot
import edu.watumull.presencify.feature.academics.add_edit_course.AddEditCourseRoot
import edu.watumull.presencify.feature.academics.add_edit_division.AddEditDivisionRoot
import edu.watumull.presencify.feature.academics.add_edit_scheme.AddEditSchemeRoot
import edu.watumull.presencify.feature.academics.add_edit_semester.AddEditSemesterRoot
import edu.watumull.presencify.feature.academics.add_edit_university.AddEditUniversityRoot
import edu.watumull.presencify.feature.academics.batch_details.BatchDetailsRoot
import edu.watumull.presencify.feature.academics.branch_details.BranchDetailsRoot
import edu.watumull.presencify.feature.academics.course_details.CourseDetailsRoot
import edu.watumull.presencify.feature.academics.dashboard.AcademicsDashboardRoot
import edu.watumull.presencify.feature.academics.division_details.DivisionDetailsRoot
import edu.watumull.presencify.feature.academics.link_unlink_course.LinkUnlinkCourseRoot
import edu.watumull.presencify.feature.academics.scheme_details.SchemeDetailsRoot
import edu.watumull.presencify.feature.academics.search_batch.SearchBatchRoot
import edu.watumull.presencify.feature.academics.search_branch.SearchBranchRoot
import edu.watumull.presencify.feature.academics.search_course.SearchCourseRoot
import edu.watumull.presencify.feature.academics.search_division.SearchDivisionRoot
import edu.watumull.presencify.feature.academics.search_scheme.SearchSchemeRoot
import edu.watumull.presencify.feature.academics.search_semester.SearchSemesterRoot
import edu.watumull.presencify.feature.academics.semester_details.SemesterDetailsRoot
import edu.watumull.presencify.feature.academics.university_details.UniversityDetailsRoot

fun NavGraphBuilder.academicsDashboard(
    onNavigateToSearchBranch: () -> Unit,
    onNavigateToSearchScheme: () -> Unit,
    onNavigateToSearchCourse: () -> Unit,
    onNavigateToLinkUnlinkCourse: () -> Unit,
    onNavigateToUniversityDetails: () -> Unit,
    onNavigateToSearchSemester: () -> Unit,
    onNavigateToSearchDivision: () -> Unit,
    onNavigateToSearchBatch: () -> Unit,
) {
    composableWithSlideTransitions<AcademicsRoutes.AcademicsDashboard> {
        AcademicsDashboardRoot(
            onNavigateToSearchBranch = onNavigateToSearchBranch,
            onNavigateToSearchScheme = onNavigateToSearchScheme,
            onNavigateToSearchCourse = onNavigateToSearchCourse,
            onNavigateToLinkUnlinkCourse = onNavigateToLinkUnlinkCourse,
            onNavigateToUniversityDetails = onNavigateToUniversityDetails,
            onNavigateToSearchSemester = onNavigateToSearchSemester,
            onNavigateToSearchDivision = onNavigateToSearchDivision,
            onNavigateToSearchBatch = onNavigateToSearchBatch
        )
    }
}

fun NavGraphBuilder.academicsNavGraph(
    onNavigateBack: () -> Unit,
    onNavigateToBranchDetails: (String) -> Unit,
    onNavigateToAddEditBranch: (String?) -> Unit,
    onNavigateToSchemeDetails: (String) -> Unit,
    onNavigateToAddEditScheme: (String?) -> Unit,
    onNavigateToAddEditUniversity: (String?) -> Unit,
    onNavigateToCourseDetails: (String) -> Unit,
    onNavigateToAddEditCourse: (String?) -> Unit,
    onNavigateToSearchCourse: (String, Int) -> Unit,
    onNavigateToSemesterDetails: (String) -> Unit,
    onNavigateToAddEditSemester: (String?) -> Unit,
    onNavigateToDivisionDetails: (String) -> Unit,
    onNavigateToAddEditDivision: (String?) -> Unit,
    onNavigateToBatchDetails: (String) -> Unit,
    onNavigateToAddEditBatch: (String?) -> Unit,
) {

    composableWithSlideTransitions<AcademicsRoutes.AddEditBranch> {
        AddEditBranchRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SearchBranch> {
        SearchBranchRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToBranchDetails = onNavigateToBranchDetails,
            onNavigateToAddEditBranch = { onNavigateToAddEditBranch(null) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.BranchDetails> {
        BranchDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditBranch = { id -> onNavigateToAddEditBranch(id) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.AddEditScheme> {
        AddEditSchemeRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SearchScheme> {
        SearchSchemeRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToSchemeDetails = onNavigateToSchemeDetails,
            onNavigateToAddEditScheme = { onNavigateToAddEditScheme(null) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SchemeDetails> {
        SchemeDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditScheme = { id -> onNavigateToAddEditScheme(id) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.AddEditUniversity> {
        AddEditUniversityRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.UniversityDetails> {
        UniversityDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToAddUniversity = { onNavigateToAddEditUniversity(null) },
            onNavigateToEditUniversity = { id -> onNavigateToAddEditUniversity(id) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.AddEditCourse> {
        AddEditCourseRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SearchCourse> {
        SearchCourseRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToCourseDetails = onNavigateToCourseDetails,
            onNavigateToAddEditCourse = { onNavigateToAddEditCourse(null) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.CourseDetails> {
        CourseDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditCourse = { id -> onNavigateToAddEditCourse(id) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.LinkUnlinkCourse> {
        LinkUnlinkCourseRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToSearchCourse = onNavigateToSearchCourse
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.AddEditBatch> {
        AddEditBatchRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SearchBatch> {
        SearchBatchRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToBatchDetails = onNavigateToBatchDetails,
            onNavigateToAddEditBatch = { onNavigateToAddEditBatch(null) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.BatchDetails> {
        BatchDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditBatch = { id -> onNavigateToAddEditBatch(id) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.AddEditDivision> {
        AddEditDivisionRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SearchDivision> {
        SearchDivisionRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToDivisionDetails = onNavigateToDivisionDetails,
            onNavigateToAddEditDivision = { onNavigateToAddEditDivision(null) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.DivisionDetails> {
        DivisionDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditDivision = { id -> onNavigateToAddEditDivision(id) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.AddEditSemester> {
        AddEditSemesterRoot(
            onNavigateBack = onNavigateBack
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SearchSemester> {
        SearchSemesterRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToSemesterDetails = onNavigateToSemesterDetails,
            onNavigateToAddEditSemester = { onNavigateToAddEditSemester(null) }
        )
    }

    composableWithSlideTransitions<AcademicsRoutes.SemesterDetails> {
        SemesterDetailsRoot(
            onNavigateBack = onNavigateBack,
            onNavigateToEditSemester = { id -> onNavigateToAddEditSemester(id) }
        )
    }

}
