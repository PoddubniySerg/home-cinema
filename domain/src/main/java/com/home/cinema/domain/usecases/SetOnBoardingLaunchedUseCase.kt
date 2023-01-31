package com.home.cinema.domain.usecases

import com.home.cinema.domain.repositories.OnBoardingRepository
import javax.inject.Inject

open class SetOnBoardingLaunchedUseCase @Inject constructor() {

    @Inject
    protected lateinit var onBoardingRepository: OnBoardingRepository

    fun execute() {
        try {
            onBoardingRepository.setOnBoardingLaunched()
        } catch (ex: java.lang.Exception) {
//          Todo handle exception
        }
    }
}