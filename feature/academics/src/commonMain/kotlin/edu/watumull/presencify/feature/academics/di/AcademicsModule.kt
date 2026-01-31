package edu.watumull.presencify.feature.academics.di

import edu.watumull.presencify.feature.academics.add_edit_batch.AddEditBatchViewModel
import edu.watumull.presencify.feature.academics.add_edit_branch.AddEditBranchViewModel
import edu.watumull.presencify.feature.academics.add_edit_course.AddEditCourseViewModel
import edu.watumull.presencify.feature.academics.add_edit_division.AddEditDivisionViewModel
import edu.watumull.presencify.feature.academics.add_edit_scheme.AddEditSchemeViewModel
import edu.watumull.presencify.feature.academics.add_edit_semester.AddEditSemesterViewModel
import edu.watumull.presencify.feature.academics.add_edit_university.AddEditUniversityViewModel
import edu.watumull.presencify.feature.academics.batch_details.BatchDetailsViewModel
import edu.watumull.presencify.feature.academics.branch_details.BranchDetailsViewModel
import edu.watumull.presencify.feature.academics.course_details.CourseDetailsViewModel
import edu.watumull.presencify.feature.academics.dashboard.AcademicsDashboardViewModel
import edu.watumull.presencify.feature.academics.division_details.DivisionDetailsViewModel
import edu.watumull.presencify.feature.academics.link_unlink_course.LinkUnlinkCourseViewModel
import edu.watumull.presencify.feature.academics.scheme_details.SchemeDetailsViewModel
import edu.watumull.presencify.feature.academics.search_batch.SearchBatchViewModel
import edu.watumull.presencify.feature.academics.search_branch.SearchBranchViewModel
import edu.watumull.presencify.feature.academics.search_course.SearchCourseViewModel
import edu.watumull.presencify.feature.academics.search_division.SearchDivisionViewModel
import edu.watumull.presencify.feature.academics.search_scheme.SearchSchemeViewModel
import edu.watumull.presencify.feature.academics.search_semester.SearchSemesterViewModel
import edu.watumull.presencify.feature.academics.semester_details.SemesterDetailsViewModel
import edu.watumull.presencify.feature.academics.university_details.UniversityDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val academicsModule = module {
    viewModel { SearchSemesterViewModel(get(), get(), get()) }
    viewModel { SearchBatchViewModel(get(), get(), get()) }
    viewModel { SearchDivisionViewModel(get(), get()) }
    viewModel { SearchCourseViewModel(get(), get(), get(), get(),get()) }
    viewModel { SearchBranchViewModel(get()) }
    viewModel { SearchSchemeViewModel(get()) }
    viewModel { LinkUnlinkCourseViewModel(get()) }
    viewModel { AcademicsDashboardViewModel() }
    viewModel { BranchDetailsViewModel(get(), savedStateHandle = get()) }
    viewModel { SchemeDetailsViewModel(get(), savedStateHandle = get()) }
    viewModel { UniversityDetailsViewModel(get()) }
    viewModel { CourseDetailsViewModel(get(), savedStateHandle = get()) }
    viewModel { SemesterDetailsViewModel(get(), savedStateHandle = get()) }
    viewModel { DivisionDetailsViewModel(get(), savedStateHandle = get()) }
    viewModel { BatchDetailsViewModel(get(), savedStateHandle = get()) }
    viewModel { AddEditBranchViewModel(get(), savedStateHandle = get()) }
    viewModel { AddEditSchemeViewModel(get(), get(), savedStateHandle = get()) }
    viewModel { AddEditUniversityViewModel(get(), savedStateHandle = get()) }
    viewModel { AddEditCourseViewModel(get(), get(), savedStateHandle = get()) }
    viewModel { AddEditSemesterViewModel(get(), get(), get(), get(), savedStateHandle = get()) }
    viewModel { AddEditDivisionViewModel(get(), get(), get(), savedStateHandle = get()) }
    viewModel { AddEditBatchViewModel(get(), get(), get(), get(), get()) }
}
