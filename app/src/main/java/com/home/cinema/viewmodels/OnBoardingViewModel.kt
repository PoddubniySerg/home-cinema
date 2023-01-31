package com.home.cinema.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel() {

    private val _exitFromOnBoardingFlow = MutableStateFlow(false)
    val exitFromOnBoardingFlow = _exitFromOnBoardingFlow.asStateFlow()

    private val _isFirstAppLaunchFlow = Channel<Boolean>()
    val isFirstAppLaunchFlow = _isFirstAppLaunchFlow.receiveAsFlow()

    //    переходим от презентации с загрузке приложения
    fun exit() {
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
                _isFirstAppLaunchFlow.send(false)
            }
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }

    //    сохраняем в репозитории информацию о том, что приложение уже было запущено
    fun onBoardingWasLaunched() {
        try {
        } catch (ex: Exception) {
//            Todo handle exception
        }
    }
}