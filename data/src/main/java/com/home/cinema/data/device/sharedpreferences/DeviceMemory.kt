package com.home.cinema.data.device.sharedpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.home.cinema.data.DataApp

object DeviceMemory {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    fun getDataSource() = DataApp.getContext().dataStore
}