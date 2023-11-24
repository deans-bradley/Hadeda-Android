package com.st10083210.hadeda

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

enum class UnitSystem {
    METRIC,
    IMPERIAL
}

data class UserPreferences(
    val unitSystem: UnitSystem,
    val distRadius: Int
)

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val UNIT_SYSTEM = stringPreferencesKey("unit_system")
        val DIST_RADIUS = intPreferencesKey("dist_radius")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { preferences ->
        val unitSystem = UnitSystem.valueOf(preferences[PreferencesKeys.UNIT_SYSTEM] ?: UnitSystem.METRIC.name)
        val distRadius = preferences[PreferencesKeys.DIST_RADIUS] ?: 25

        UserPreferences(unitSystem, distRadius)
    }

    suspend fun updateUnitSystem(unitSystem: UnitSystem) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UNIT_SYSTEM] = unitSystem.name
        }
    }

    suspend fun updateDistRadius(distRadius: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DIST_RADIUS] = distRadius
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val unitSystem =
            UnitSystem.valueOf(
                preferences[PreferencesKeys.UNIT_SYSTEM] ?: UnitSystem.METRIC.name
            )

        val distRadius = 25

        return UserPreferences(unitSystem, distRadius)
    }
}