package edu.watumull.presencify.di
import edu.watumull.presencify.AppViewModel
import edu.watumull.presencify.core.data.di.coreDataModule
import edu.watumull.presencify.feature.academics.di.academicsModule
import edu.watumull.presencify.feature.admin.auth.di.adminAuthModule
import edu.watumull.presencify.feature.admin.mgt.di.adminMgtModule
import edu.watumull.presencify.feature.onboarding.di.onboardingModule
import edu.watumull.presencify.feature.schedule.di.scheduleModule
import edu.watumull.presencify.feature.users.di.usersModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            module {
                viewModel { AppViewModel(get()) }
            },

            coreDataModule,

            // feature modules
            onboardingModule,
            adminAuthModule,
            adminMgtModule,
            usersModule,
            academicsModule,
            scheduleModule
        )
    }
}
