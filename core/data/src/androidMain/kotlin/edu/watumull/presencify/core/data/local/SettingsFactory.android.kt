package edu.watumull.presencify.core.data.local

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class SettingsFactory actual constructor(platformContext: PlatformContext) {
    private val platformContext: PlatformContext = platformContext
    actual fun create(name: String): Settings {
        val masterKey = MasterKey.Builder(platformContext.androidContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPrefs = EncryptedSharedPreferences.create(
            platformContext.androidContext,
            name,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return SharedPreferencesSettings(sharedPrefs)
    }
}
