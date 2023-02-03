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

    private val _exitFromOnBoardingFlow = MutableStateFlow(false)
    val exitFromOnBoardingFlow = _exitFromOnBoardingFlow.asStateFlow()

    private val _isFirstAppLaunchFlow = Channel<Boolean>()
    val isFirstAppLaunchFlow = _isFirstAppLaunchFlow.receiveAsFlow()

    //    переходим от презентации с загрузке приложения
    fun exit() {
        setOnBoardingLaunched()
        try {
            _exitFromOnBoardingFlow.value = true
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }

    //    проверяем запускалось ли приложение раньше
    fun isFirstLaunch() {
        try {
            viewModelScope.launch {
                _isFirstAppLaunchFlow.send(
                    checkIfAppWasLaunchUseCase.execute().isFirstLaunch
                )
            }
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }

    //    сохраняем в репозитории информацию о том, что приложение уже было запущено
    private fun setOnBoardingLaunched() {
        try {
            setOnBoardingLaunchedUseCase.execute()
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }
}