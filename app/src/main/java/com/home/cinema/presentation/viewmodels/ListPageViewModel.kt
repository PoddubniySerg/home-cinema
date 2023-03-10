package com.home.cinema.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.usecases.ListPageGetCollectionUseCase
import com.home.cinema.domain.usecases.MovieCheckBeenViewedUseCase
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

    private val _stateFlow = MutableStateFlow(States.STARTING)
    val stateFlow = _stateFlow.asStateFlow()

    private val _premiersFlow = Channel<List<Movie>>()
    val premiersFlow = _premiersFlow.receiveAsFlow()

    suspend fun getPremiers() {
        try {
            _stateFlow.value = States.LOADING
            _premiersFlow.send(
                checkMoviesBeenViewed(listPageGetCollectionUseCase.getPremiers().movies)
                    ?: emptyList()
            )
        } catch (ex: Exception) {
//            TODO exception handler
        } finally {
            _stateFlow.value = States.COMPLETE
        }
    }

    val pagedMovies: Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            ListPagePagingSource(
                getMoviesByPage = { page -> getMovies(page) }
            )
        }
    ).flow.cachedIn(viewModelScope)

    private suspend fun getMovies(page: Int): List<Movie> {
        return checkMoviesBeenViewed(listPageGetCollectionUseCase.getMovies(page).movies)
            ?: emptyList()
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

    private suspend fun checkMoviesBeenViewed(movies: List<Movie>?): List<Movie>? {
        viewModelScope.launch {
            movies?.forEach { movie ->
                movie.seen = movieCheckBeenViewedUseCase.execute(movie.id).movieBeenViewed
            }
        }.join()
        return movies
    }
}