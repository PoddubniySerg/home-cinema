package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.domain.models.entities.page.home.PremierMovie
import com.home.cinema.domain.usecases.home.GetPremiersUseCase
import com.home.cinema.enums.States
import com.home.cinema.model.HomeMoviesCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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

    private val collections = mutableListOf<HomeMoviesCollection>()
    private val collectionsAreReady = MutableStateFlow(false)

    fun getCollections(collectionNames: Array<String>) {
        try {
            _stateFlow.value = States.LOADING
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
                        1 -> {
                            collections.add(
                                HomeMoviesCollection(
                                    collectionNames[i],
                                    movies = getPremiers() ?: break
                                )
                            )
                        }
                        2 -> {
                            collections.add(
                                HomeMoviesCollection(
                                    collectionNames[i],
                                    movies = getPremiers() ?: break
                                )
                            )
                        }
                        3 -> {
                            collections.add(
                                HomeMoviesCollection(
                                    collectionNames[i],
                                    movies = getPremiers() ?: break
                                )
                            )
                        }
                        4 -> {
                            collections.add(
                                HomeMoviesCollection(
                                    collectionNames[i],
                                    movies = getPremiers() ?: break
                                )
                            )
                        }
                        5 -> {
                            collections.add(
                                HomeMoviesCollection(
                                    collectionNames[i],
                                    movies = getPremiers() ?: break
                                )
                            )
                        }
                        else -> break
                    }
                }
                collectionsAreReady.value = true
            }
        } catch (ex: Exception) {
//            TODO exception handler
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }

    fun getCollections() {
        try {
            collectionsAreReady.onEach { collectionsAreReady ->
                if (collectionsAreReady) _collectionsFlow.send(collections.toList())
            }.launchIn(viewModelScope)
        } catch (ex: Exception) {
//            TODO exception handler
        }
    }

    private suspend fun getPremiers(): List<PremierMovie>? {
        return try {
            getPremiersUseCase.execute().premiers
        } catch (ex: Exception) {
            //            TODO exception handler
            null
        }
    }
}