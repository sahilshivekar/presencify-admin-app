package edu.watumull.presencify.core.data.local

import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings

actual class SettingsFactory actual constructor(@Suppress("UNUSED_PARAMETER") platformContext: PlatformContext) {
    @OptIn(ExperimentalSettingsImplementation::class)
    actual fun create(name: String): Settings {
        // Use iOS Keychain via KeychainSettings; constructor uses `service` parameter name
        return KeychainSettings(service = name)
    }
}