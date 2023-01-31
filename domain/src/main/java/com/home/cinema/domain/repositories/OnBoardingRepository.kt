package com.home.cinema.domain.repositories

interface OnBoardingRepository {

    fun isAppWasLaunch(): Boolean

    fun setOnBoardingLaunched()
}