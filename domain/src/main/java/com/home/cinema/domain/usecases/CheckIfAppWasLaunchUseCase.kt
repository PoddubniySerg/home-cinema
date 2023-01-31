package com.home.cinema.domain.usecases

import com.home.cinema.domain.models.results.onboarding.CheckIfAppWasLaunchResult
import com.home.cinema.domain.repositories.OnBoardingRepository
import javax.inject.Inject

open class CheckIfAppWasLaunchUseCase @Inject constructor() {

    @Inject
    protected lateinit var onBoardingRepository: OnBoardingRepository

    fun execute(): CheckIfAppWasLaunchResult {
        return try {
            CheckIfAppWasLaunchResult(!onBoardingRepository.isAppWasLaunch())
        } catch (ex: java.lang.Exception) {
//          Todo handle exception
            CheckIfAppWasLaunchResult(true)
        }
    }
}