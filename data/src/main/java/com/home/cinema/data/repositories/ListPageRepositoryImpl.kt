package com.home.cinema.data.repositories

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.MoviesSource
import com.home.cinema.data.network.retrofit.api.page.ListPageApi
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.listpage.*
import com.home.cinema.domain.repositories.ListPageRepository

class ListPageRepositoryImpl : ListPageRepository {
    companion object {
        const val PREMIERS_COLLECTION_PAGES = 1
    }

    private val loader = MoviesSource().getLoader(ListPageApi::class.java)

    override suspend fun getPopular(param: ListPageGetPopularByPageParam): List<Movie>? {
        val response = loader.getPopular(param.pageNumber)
        return response?.body()?.films
    }

    override suspend fun getPremiers(param: ListPageGetPremiersParam): List<PremierMovie>? {
        return if (param.page == PREMIERS_COLLECTION_PAGES) {
            val response = loader.getPremiers(param.year, param.month)
            response.body()?.items
                ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
        } else {
            null
        }
    }

    override suspend fun getSeries(param: ListPageGetSeriesParam): List<Movie>? {
        val response = loader.getTvSeries(param.page)
        return response.body()?.films
    }

    override suspend fun getTop250(param: ListPageGetTop250Param): List<Movie>? {
        val response = loader.getTop250(param.page)
        return response.body()?.films
    }

    override suspend fun getMoviesByFilter(param: ListPageGetMoviesByFilterParam): List<Movie>? {
        val response = loader.getByFilters(param.country, param.genre, param.page)
        return response.body()?.films
    }
}