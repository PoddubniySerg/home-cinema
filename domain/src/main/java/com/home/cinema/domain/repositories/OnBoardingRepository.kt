package com.home.cinema.domain.repositories

interface OnBoardingRepository {

    suspend fun isAppWasLaunch(): Boolean

    suspend fun setOnBoardingLaunched()
}