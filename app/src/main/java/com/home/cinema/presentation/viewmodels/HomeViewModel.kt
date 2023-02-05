package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.domain.usecases.home.GetPremiersUseCase
import com.home.cinema.enums.States
import com.home.cinema.model.HomeMoviesCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor() : ViewModel() {

    @Inject
    protected lateinit var getPremiersUseCase: GetPremiersUseCase

    private val _stateFlow = MutableStateFlow(States.STARTING)
    val stateFlow = _stateFlow.asStateFlow()

    private val _collectionsFlow = Channel<List<HomeMoviesCollection>>()
    val collectionsFlow = _collectionsFlow.receiveAsFlow()

    fun getCollections(collectionNames: Array<String>) {
        try {
            _stateFlow.value = States.LOADING
            val collections = mutableListOf<HomeMoviesCollection>()
            viewModelScope.launch {
                for (i in collectionNames.indices) {
                    when (i) {
                        0 -> {
                            collections.add(
                                HomeMoviesCollection(
                                    collectionNames[i],
                                    movies = getPremiers() ?: break
                                )
                            )
                        }
//                1 -> {}
//                2 -> {}
//                3 -> {}
//                4 -> {}
//                5 -> {}
                        else -> break
                    }
                }
                _collectionsFlow.send(collections.toList())
            }
        } catch (ex: Exception) {
//            TODO exception handler
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }

    private suspend fun getPremiers(): List<Movie>? {
        try {
            return getPremiersUseCase.execute().premiers
        } catch (ex: Exception) {
//            TODO exception handler
            return null
        }
    }
}