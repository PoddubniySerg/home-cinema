package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.domain.models.entities.collections.movies.Movie
import com.home.cinema.domain.usecases.MovieCheckBeenViewedUseCase
import com.home.cinema.domain.usecases.home.*
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

    @Inject
    protected lateinit var getPopularUseCase: GetPopularUseCase

    @Inject
    protected lateinit var getRandomCollectionUseCase: GetRandomCollectionUseCase

    @Inject
    protected lateinit var getTop250UseCase: GetTop250UseCase

    @Inject
    protected lateinit var getTVSeriesUseCase: GetTVSeriesUseCase

    @Inject
    protected lateinit var movieCheckBeenViewedUseCase: MovieCheckBeenViewedUseCase

    private val _stateFlow = MutableStateFlow(States.STARTING)
    val stateFlow = _stateFlow.asStateFlow()

    private val _collectionsFlow = Channel<List<HomeMoviesCollection>>()
    val collectionsFlow = _collectionsFlow.receiveAsFlow()

    private val collections = mutableListOf<HomeMoviesCollection>()
    private val collectionsAreReady = MutableStateFlow(false)

    fun getCollections(collectionNames: Array<String>) {
        try {
            loadCollections(collectionNames)
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

    private fun loadCollections(collectionNames: Array<String>) {
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
                                movies = getPopular() ?: break
                            )
                        )
                    }
                    2 -> {
                        val collection = getRandomCollectionUseCase.execute()
                        collections.add(
                            HomeMoviesCollection(
                                collection.name,
                                movies = checkMoviesBeenViewed(collection.movies) ?: break
                            )
                        )
                    }
                    3 -> {
                        val collection = getRandomCollectionUseCase.execute()
                        collections.add(
                            HomeMoviesCollection(
                                collection.name,
                                movies = checkMoviesBeenViewed(collection.movies) ?: break
                            )
                        )
                    }
                    4 -> {
                        collections.add(
                            HomeMoviesCollection(
                                collectionNames[i],
                                movies = getTop250() ?: break
                            )
                        )
                    }
                    5 -> {
                        collections.add(
                            HomeMoviesCollection(
                                collectionNames[i],
                                movies = getTVSeries() ?: break
                            )
                        )
                    }
                    else -> break
                }
            }
            collectionsAreReady.value = true
        }
    }

    private suspend fun getPremiers(): List<Movie>? {
        return try {
            checkMoviesBeenViewed(getPremiersUseCase.execute().movies)
        } catch (ex: Exception) {
            //            TODO exception handler
            null
        }
    }

    private suspend fun getPopular(): List<Movie>? {
        return try {
            checkMoviesBeenViewed(getPopularUseCase.execute().movies)
        } catch (ex: Exception) {
            //            TODO exception handler
            null
        }
    }

    private suspend fun getTop250(): List<Movie>? {
        return try {
            checkMoviesBeenViewed(getTop250UseCase.execute().movies)
        } catch (ex: Exception) {
            //            TODO exception handler
            null
        }
    }

    private suspend fun getTVSeries(): List<Movie>? {
        return try {
            checkMoviesBeenViewed(getTVSeriesUseCase.execute().movies)
        } catch (ex: Exception) {
            //            TODO exception handler
            null
        }
    }

    private suspend fun checkMoviesBeenViewed(movies: List<Movie>?): List<Movie>? {
        viewModelScope.launch {
            movies?.forEach { movie ->
                movie.seen = movieCheckBeenViewedUseCase.execute(movie.id).movieBeenViewed
            }
        }.join()
        return movies
    }
}