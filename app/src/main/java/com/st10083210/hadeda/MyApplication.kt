package com.st10083210.hadeda

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private const val mapPreferences = "user_preferences"
private val Context.dataStore by preferencesDataStore(name = mapPreferences)

class MyApplication : Application() {

    val userPreferences: UserPreferencesRepository by lazy {
        UserPreferencesRepository(dataStore)
    }

    override fun onCreate() {
        super.onCreate()

    }
}