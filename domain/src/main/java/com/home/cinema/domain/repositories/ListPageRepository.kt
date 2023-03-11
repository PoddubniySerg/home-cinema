package com.home.cinema.domain.repositories

import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.listpage.*

interface ListPageRepository {

    suspend fun getPopular(param: ListPageGetPopularByPageParam): List<Movie>?

    suspend fun getPremiers(param: ListPageGetPremiersParam): List<PremierMovie>?

    suspend fun getSeries(param: ListPageGetSeriesParam): List<Movie>?

    suspend fun getTop250(param: ListPageGetTop250Param): List<Movie>?

    suspend fun getMoviesByFilter(param: ListPageGetMoviesByFilterParam): List<Movie>?
}