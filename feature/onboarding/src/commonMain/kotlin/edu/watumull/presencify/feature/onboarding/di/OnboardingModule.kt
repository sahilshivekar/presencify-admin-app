package edu.watumull.presencify.feature.onboarding.di

import edu.watumull.presencify.feature.onboarding.select_role.SelectRoleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    viewModel { SelectRoleViewModel() }
}