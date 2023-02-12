package com.home.cinema.domain.repositories.page.onboarding

interface OnBoardingRepository {

    suspend fun isAppWasLaunch(): Boolean

    suspend fun setOnBoardingLaunched()
}