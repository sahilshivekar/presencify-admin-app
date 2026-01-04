package edu.watumull.presencify.core.data.local

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual class SettingsFactory actual constructor(@Suppress("UNUSED_PARAMETER") platformContext: PlatformContext) {
    actual fun create(name: String): Settings {
        val node = Preferences.userRoot().node("edu.watumull.presencify").node(name)
        return PreferencesSettings(node)
    }
}
