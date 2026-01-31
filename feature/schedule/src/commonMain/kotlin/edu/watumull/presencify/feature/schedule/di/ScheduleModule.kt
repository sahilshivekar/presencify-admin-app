package edu.watumull.presencify.feature.schedule.di

import edu.watumull.presencify.feature.schedule.dashboard.ScheduleDashboardViewModel
import edu.watumull.presencify.feature.schedule.search_class.SearchClassViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val scheduleModule = module {
    viewModel { ScheduleDashboardViewModel() }
    viewModel {
        SearchClassViewModel(
            classSessionRepository = get(),
            teacherRepository = get(),
            roomRepository = get(),
            batchRepository = get(),
            courseRepository = get(),
            savedStateHandle = get()
        )
    }
}

