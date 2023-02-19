package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.data.mocks.MockAccountCollections
import com.home.cinema.data.mocks.MockPremiers
import com.home.cinema.domain.models.entities.collections.account.AccountCollection
import com.home.cinema.domain.models.entities.collections.movies.Movie
import com.home.cinema.enums.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {

    private val movies = MockPremiers()
    private val collections = MockAccountCollections()

    private val _stateFlow = MutableStateFlow(States.STARTING)
    val stateFlow = _stateFlow.asStateFlow()

    private val _moviesChannel = Channel<List<Movie>>()
    val moviesFlow = _moviesChannel.receiveAsFlow()

    private val _collectionsChannel = Channel<List<AccountCollection>>()
    val collectionsFlow = _collectionsChannel.receiveAsFlow()

    fun getMovies() {
        try {
            _stateFlow.value = States.LOADING
            viewModelScope.launch {
                _moviesChannel.send(movies.premiers)
            }
        } catch (ex: java.lang.Exception) {
//            TODO exception handler
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }

    fun getCollections() {
        try {
            _stateFlow.value = States.LOADING
            viewModelScope.launch {
                _collectionsChannel.send(collections.collections)
            }
        } catch (ex: java.lang.Exception) {
//            TODO exception handler
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }
}