package edu.watumull.presencify.core.data.local

import com.russhwolf.settings.Settings

expect class SettingsFactory(platformContext: PlatformContext) {
    fun create(name: String = "user_prefs"): Settings
}