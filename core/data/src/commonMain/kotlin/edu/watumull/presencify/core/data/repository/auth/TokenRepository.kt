package edu.watumull.presencify.core.data.repository.auth

import com.russhwolf.settings.Settings
import edu.watumull.presencify.core.data.Constants

class TokenRepository(
    private val settings: Settings
) {

    fun saveAccessToken(token: String) {
        settings.putString(Constants.ACCESS_TOKEN_KEY, token)
    }

    fun saveRefreshToken(token: String) {
        settings.putString(Constants.REFRESH_TOKEN_KEY, token)
    }

    fun readAccessToken(): String? {
        return settings.getStringOrNull(Constants.ACCESS_TOKEN_KEY)
    }

    fun readRefreshToken(): String? {
        return settings.getStringOrNull(Constants.REFRESH_TOKEN_KEY)
    }

    fun clearTokens() {
        settings.remove(Constants.ACCESS_TOKEN_KEY)
        settings.remove(Constants.REFRESH_TOKEN_KEY)
    }
}