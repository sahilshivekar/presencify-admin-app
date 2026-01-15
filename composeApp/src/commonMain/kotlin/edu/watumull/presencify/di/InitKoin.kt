package edu.watumull.presencify.di
import edu.watumull.presencify.AppViewModel
import edu.watumull.presencify.core.data.di.coreDataModule
import edu.watumull.presencify.feature.onboarding.di.onboardingModule
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
        )
    }
}
