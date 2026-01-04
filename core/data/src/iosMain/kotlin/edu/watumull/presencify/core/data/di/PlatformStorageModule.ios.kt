package edu.watumull.presencify.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import edu.watumull.presencify.core.data.local.DataStoreFactory
import edu.watumull.presencify.core.data.local.PlatformContext
import edu.watumull.presencify.core.data.local.SettingsFactory
import edu.watumull.presencify.core.data.local.dataStorePath
import org.koin.dsl.module

actual val platformStorageModule = module {
    single { SettingsFactory(PlatformContext()) }
    // DataStore for iOS
    single<DataStore<Preferences>> {
        DataStoreFactory.create { dataStorePath() }
    }
}