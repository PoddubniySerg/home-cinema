package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.domain.usecases.onboarding.CheckIfAppWasLaunchUseCase
import com.home.cinema.domain.usecases.onboarding.SetOnBoardingLaunchedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class OnBoardingViewModel @Inject constructor() : ViewModel() {

    @Inject
    protected lateinit var setOnBoardingLaunchedUseCase: SetOnBoardingLaunchedUseCase

    @Inject
    protected lateinit var checkIfAppWasLaunchUseCase: CheckIfAppWasLaunchUseCase

    private val _exitFromOnBoardingFlow = Channel<Boolean>()
    val exitFromOnBoardingFlow = _exitFromOnBoardingFlow.receiveAsFlow()

    private val _isFirstAppLaunchFlow = Channel<Boolean>()
    val isFirstAppLaunchFlow = _isFirstAppLaunchFlow.receiveAsFlow()

    //    переходим от презентации с загрузке приложения
    fun exit() {
        setOnBoardingLaunched()
        try {
            viewModelScope.launch { _exitFromOnBoardingFlow.send(true) }
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }

    //    проверяем запускалось ли приложение раньше
    suspend fun isFirstLaunch() {
        try {
            _isFirstAppLaunchFlow.send(
                checkIfAppWasLaunchUseCase.execute().isFirstLaunch
            )
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }

    //    сохраняем в репозитории информацию о том, что приложение уже было запущено
    private fun setOnBoardingLaunched() {
        viewModelScope.launch {
            try {
                setOnBoardingLaunchedUseCase.execute()
            } catch (ex: Exception) {
//            Todo handle exception
            }
        }
    }
}