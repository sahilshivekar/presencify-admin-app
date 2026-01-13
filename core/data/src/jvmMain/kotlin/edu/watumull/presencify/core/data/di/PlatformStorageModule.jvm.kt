package edu.watumull.presencify.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import edu.watumull.presencify.core.data.local.*
import org.koin.dsl.module

actual val platformStorageModule = module {
    single { SettingsFactory(PlatformContext()) }
    single<StringCipher> { BasicObfuscationCipher() }
    // DataStore for JVM
    single<DataStore<Preferences>> {
        DataStoreFactory.create { dataStorePath() }
    }
}
