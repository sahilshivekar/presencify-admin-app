package edu.watumull.presencify.core.data.repository.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import edu.watumull.presencify.core.data.Constants
import edu.watumull.presencify.core.domain.model.auth.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoleRepository(
    private val dataStore: DataStore<Preferences>
) {

    private val userRoleKey = stringPreferencesKey(Constants.USER_ROLE_KEY)

    suspend fun saveUserRole(role: UserRole) {
        dataStore.edit { preferences ->
            preferences[userRoleKey] = role.name
        }
    }

    fun getUserRole(): Flow<UserRole?> {
        return dataStore.data.map { preferences ->
            preferences[userRoleKey]?.let { roleString ->
                try {
                    UserRole.valueOf(roleString)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }
        }
    }

    suspend fun clearUserRole() {
        dataStore.edit { preferences ->
            preferences.remove(userRoleKey)
        }
    }
}