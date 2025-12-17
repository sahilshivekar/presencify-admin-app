package edu.watumull.presencify.admin.core.di

import edu.watumull.presencify.admin.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
}
