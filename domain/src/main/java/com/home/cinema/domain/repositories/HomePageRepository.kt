package com.home.cinema.domain.repositories

import com.home.cinema.domain.models.entities.filters.CountriesAndGenres
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.page.home.HomeGetMoviesByFilterParam
import com.home.cinema.domain.models.params.page.home.HomeGetPremiersParam

interface HomePageRepository {

    suspend fun getCountriesAndGenres(): CountriesAndGenres

    suspend fun getPremiers(param: HomeGetPremiersParam): List<PremierMovie>?

    suspend fun getMoviesByFilter(param: HomeGetMoviesByFilterParam): List<Movie>?

    suspend fun getPopular(): List<Movie>

    suspend fun getTop250(): List<Movie>

    suspend fun getSeries(): List<Movie>
}