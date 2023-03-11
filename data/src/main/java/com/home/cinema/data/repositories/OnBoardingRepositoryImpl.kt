package com.home.cinema.data.repositories

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.home.cinema.data.device.sharedpreferences.DeviceMemory
import com.home.cinema.domain.repositories.OnBoardingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OnBoardingRepositoryImpl :
    OnBoardingRepository {

    private val isFirstAppLaunchKey = "is_first_app_launch"
    private val store = DeviceMemory.getDataSource()

    override suspend fun isAppWasLaunch(): Boolean {
        var isLaunched: Boolean? = null
        CoroutineScope(Dispatchers.IO).launch {
            store.data.collect { preferences ->
                isLaunched =
                    preferences[booleanPreferencesKey(isFirstAppLaunchKey)] ?: false
                cancel()
            }
        }.join()
        return isLaunched!!
    }

    override suspend fun setOnBoardingLaunched() {
        store.edit { preferences ->
            preferences[booleanPreferencesKey(isFirstAppLaunchKey)] = true
        }
    }
}