package edu.watumull.presencify.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import edu.watumull.presencify.core.data.local.BasicObfuscationCipher
import edu.watumull.presencify.core.data.local.DataStoreFactory
import edu.watumull.presencify.core.data.local.PlatformContext
import edu.watumull.presencify.core.data.local.SettingsFactory
import edu.watumull.presencify.core.data.local.StringCipher
import edu.watumull.presencify.core.data.local.dataStorePath
import org.koin.dsl.module

actual val platformStorageModule = module {
    single { SettingsFactory(PlatformContext()) }
    single<StringCipher> { BasicObfuscationCipher() }
    // DataStore for JVM
    single<DataStore<Preferences>> {
        DataStoreFactory.create { dataStorePath() }
    }
}
