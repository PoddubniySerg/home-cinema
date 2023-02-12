package com.home.cinema.data.repositories.page.onboarding

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.home.cinema.data.DataApp
import com.home.cinema.domain.repositories.page.onboarding.OnBoardingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingSharedPrefsRepository : OnBoardingRepository {

    companion object {
        const val ON_BOARDING_WAS_LAUNCHED = "on_boarding_was_launching"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        private var isLaunched: Boolean? = null
    }

    init {
        CoroutineScope(Dispatchers.Main).launch { setIsLaunched() }
    }

    override suspend fun isAppWasLaunch(): Boolean {
        while (isLaunched == null) {
            delay(200)
        }
        return isLaunched!!
    }

    override suspend fun setOnBoardingLaunched() {
        DataApp.getContext().dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(ON_BOARDING_WAS_LAUNCHED)] = true
        }
    }

    private suspend fun setIsLaunched() {
        DataApp.getContext().dataStore.data.collect { preferences ->
            isLaunched =
                preferences[booleanPreferencesKey(ON_BOARDING_WAS_LAUNCHED)] ?: false
        }
    }
}