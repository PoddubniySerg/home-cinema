package com.home.cinema.data.repositories.page

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.MoviesSource
import com.home.cinema.data.network.retrofit.api.page.HomePageApi
import com.home.cinema.domain.models.entities.filters.CountriesAndGenres
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.page.home.HomeGetMoviesByFilterParam
import com.home.cinema.domain.models.params.page.home.HomeGetPremiersParam
import com.home.cinema.domain.repositories.HomePageRepository

class HomePageRepositoryImpl : HomePageRepository {

    private val loader = MoviesSource().getLoader(HomePageApi::class.java)

    override suspend fun getCountriesAndGenres(): CountriesAndGenres {
        val response = loader.getCountriesAndGenres()
        return response.body()
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getPremiers(param: HomeGetPremiersParam): List<PremierMovie> {
        val response = loader.getPremiers(param.year, param.month)
        return response.body()?.items
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getMoviesByFilter(param: HomeGetMoviesByFilterParam): List<Movie>? {
        val response = loader.getByFilters(param.country, param.genre)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getPopular(): List<Movie> {
        val response = loader.getPopular()
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getTop250(): List<Movie> {
        val response = loader.getTop250()
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getSeries(): List<Movie> {
        val response = loader.getTvSeries()
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}