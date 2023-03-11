package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.usecases.ListPageGetCollectionUseCase
import com.home.cinema.domain.usecases.MovieCheckBeenViewedUseCase
import com.home.cinema.domain.util.CollectionType
import com.home.cinema.enums.States
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class ListPageViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_SIZE = 1
    }

    @Inject
    protected lateinit var listPageGetCollectionUseCase: ListPageGetCollectionUseCase

    @Inject
    protected lateinit var movieCheckBeenViewedUseCase: MovieCheckBeenViewedUseCase

    private val _stateFlow = MutableStateFlow(States.START)
    val stateFlow = _stateFlow.asStateFlow()

    private lateinit var pager: Pager<Int, Movie>
    val pagedMovies: Flow<PagingData<Movie>> get() = pager.flow.cachedIn(viewModelScope)

    private val _collectionTypeChannel = Channel<CollectionType>()
    val collectionTypeFlow = _collectionTypeChannel.receiveAsFlow()

    private lateinit var _collectionType: CollectionType
    private val collectionType get() = _collectionType

    private lateinit var _collectionName: String
    val collectionName get() = _collectionName

    fun openCollection(collectionType: CollectionType, collectionName: String) {
        _collectionType = collectionType
        _collectionName = collectionName
        pager =
            Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { ListPagePagingSource(getMoviesByPage = this::getMovies) }
            )
        try {
            viewModelScope.launch { _collectionTypeChannel.send(collectionType) }
        } catch (ex: Exception) {
//            TODO exception handler
        }
    }

    private suspend fun getMovies(page: Int): List<Movie> {
        return try {
            if (page > 1) {
                _stateFlow.value = States.LOADING
            }
            listPageGetCollectionUseCase.execute(
                page,
                collectionType
            ).movies
                ?: emptyList()
        } catch (ex: Exception) {
            //            TODO exception handler
            emptyList()
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }

    private class ListPagePagingSource(private val getMoviesByPage: suspend (Int) -> List<Movie>) :
        PagingSource<Int, Movie>() {

        override fun getRefreshKey(state: PagingState<Int, Movie>): Int =
            FIRST_PAGE

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
            val page = params.key ?: 1
            return kotlin.runCatching {
                getMoviesByPage(page)
            }.fold(
                onSuccess = {
                    LoadResult.Page(
                        data = it,
                        prevKey = null,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                },
                onFailure = {
                    LoadResult.Error(it)
                }
            )
        }
    }
}