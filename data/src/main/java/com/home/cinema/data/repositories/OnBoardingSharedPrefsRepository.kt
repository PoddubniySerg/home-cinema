package com.home.cinema.data.repositories

import android.content.Context
import com.home.cinema.domain.repositories.OnBoardingRepository

class OnBoardingSharedPrefsRepository(context: Context) : OnBoardingRepository {

    companion object {
        const val SHARED_PREFS_NAME = "shared_prefs_name"
        const val ON_BOARDING_WAS_LAUNCHED = "on_boarding_was_launching"
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun isAppWasLaunch(): Boolean {
        return sharedPreferences.getBoolean(ON_BOARDING_WAS_LAUNCHED, false)
    }

    override fun setOnBoardingLaunched() {
        sharedPreferences
            .edit()
            .putBoolean(ON_BOARDING_WAS_LAUNCHED, true)
            .apply()
    }
}