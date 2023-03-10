package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.domain.models.entities.collections.HomeCollection
import com.home.cinema.domain.usecases.HomeGetCollectionsUseCase
import com.home.cinema.enums.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor() : ViewModel() {

    @Inject
    protected lateinit var homeGetCollectionsUseCase: HomeGetCollectionsUseCase

    private val _stateFlow = MutableStateFlow(States.STARTING)
    val stateFlow = _stateFlow.asStateFlow()

    private val _collectionsFlow = MutableStateFlow(emptyList<HomeCollection>())
    val collectionsFlow = _collectionsFlow.asStateFlow()

    fun getCollections(collectionNames: Array<String>) {
        viewModelScope.launch {
            try {
                _collectionsFlow.value =
                    homeGetCollectionsUseCase.execute(collectionNames.asList()).collections
            } catch (ex: Exception) {
//            TODO exception handler
            } finally {
                _stateFlow.value = States.COMPLETE
            }
        }
    }
}