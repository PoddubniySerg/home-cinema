package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.results.AccountGetCollectionsResult
import com.home.cinema.domain.usecases.AccountGetCollectionsUseCase
import com.home.cinema.enums.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AccountViewModel @Inject constructor() : ViewModel() {

    @Inject
    protected lateinit var accountGetCollectionsUseCase: AccountGetCollectionsUseCase

    private val _stateFlow = MutableStateFlow(States.START)
    val stateFlow = _stateFlow.asStateFlow()

    private val _seenChannel = Channel<List<Movie>>()
    val seenFlow = _seenChannel.receiveAsFlow()

    private val _interestedChannel = Channel<List<Movie>>()
    val interestedFlow = _interestedChannel.receiveAsFlow()

    private val _collectionsChannel = Channel<List<AccountCollection>>()
    val collectionsFlow = _collectionsChannel.receiveAsFlow()

    private lateinit var collections: AccountGetCollectionsResult

    fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _stateFlow.value = States.LOADING
                collections = accountGetCollectionsUseCase.execute()
                val userCollections = mutableListOf<AccountCollection>()
                collections.collections.forEach { collection ->
                    when (collection.name) {
                        Constants.ACCOUNT_SEEN_COLLECTION_KEY,
                        Constants.ACCOUNT_INTERESTED_COLLECTION_KEY -> getMovies(collection)
                        Constants.ACCOUNT_FAVORITE_COLLECTION_KEY -> userCollections.add(
                            index = 0,
                            collection
                        )
                        Constants.ACCOUNT_WILL_VIEW_COLLECTION_KEY -> userCollections.add(
                            index = 1,
                            collection
                        )
                        else -> userCollections.add(collection)
                    }
                }
                _collectionsChannel.send(userCollections)
            } catch (ex: java.lang.Exception) {
//            TODO exception handler
            } finally {
                _stateFlow.value = States.COMPLETE
            }
        }
    }

    fun clearSeen() {}

    fun clearInterested() {}

    private suspend fun getMovies(collection: AccountCollection) {
        try {
            _stateFlow.value = States.LOADING
            if (collection.name == Constants.ACCOUNT_SEEN_COLLECTION_KEY) {
                _seenChannel.send(
                    collections.moviesByCollectionId[collection.id] ?: emptyList()
                )
            } else if (collection.name == Constants.ACCOUNT_INTERESTED_COLLECTION_KEY) {
                _interestedChannel.send(
                    collections.moviesByCollectionId[collection.id] ?: emptyList()
                )
            }
        } catch (ex: java.lang.Exception) {
//            TODO exception handler
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }
}