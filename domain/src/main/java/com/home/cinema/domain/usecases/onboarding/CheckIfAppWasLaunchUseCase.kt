package com.home.cinema.domain.usecases.onboarding

import com.home.cinema.domain.models.results.page.onboarding.CheckIfAppWasLaunchResult
import com.home.cinema.domain.repositories.page.onboarding.OnBoardingRepository
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