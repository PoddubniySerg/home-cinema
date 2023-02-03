package com.home.cinema.domain.repositories.page.onboarding

interface OnBoardingRepository {

    fun isAppWasLaunch(): Boolean

    fun setOnBoardingLaunched()
}