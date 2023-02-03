package com.home.cinema.data.page.onboarding

import android.content.Context
import com.home.cinema.data.DataApp
import com.home.cinema.domain.repositories.page.onboarding.OnBoardingRepository

class OnBoardingSharedPrefsRepository : OnBoardingRepository {

    companion object {
        const val SHARED_PREFS_NAME = "shared_prefs_name"
        const val ON_BOARDING_WAS_LAUNCHED = "on_boarding_was_launching"
    }

    private val sharedPreferences =
        DataApp.getContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

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