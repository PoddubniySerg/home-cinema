package com.home.cinema.domain.usecases.onboarding

import com.home.cinema.domain.repositories.OnBoardingRepository
import javax.inject.Inject

open class SetOnBoardingLaunchedUseCase @Inject constructor() {

    @Inject
    protected lateinit var onBoardingRepository: OnBoardingRepository

    suspend fun execute() {
        try {
            onBoardingRepository.setOnBoardingLaunched()
        } catch (ex: java.lang.Exception) {
//          Todo handle exception
        }
    }
}