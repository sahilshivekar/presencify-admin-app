package edu.watumull.presencify.core.data.di

import org.koin.dsl.module

val coreDataModule = module {
    // Include all categorized modules
    includes(storageModule)
    includes(networkModule)
    // includes(repositoryModule)  // TODO: Add when RepositoryModule.kt is created

}