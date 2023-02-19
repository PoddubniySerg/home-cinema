package com.home.cinema.data.repositories.page.home

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.NetworkStore
import com.home.cinema.data.network.retrofit.api.page.home.PopularApi
import com.home.cinema.domain.models.entities.collections.movies.Movie
import com.home.cinema.domain.repositories.page.home.PopularRepository

class HomePopularRepository : PopularRepository {

    private val loader = NetworkStore.getLoader(PopularApi::class.java)

    override suspend fun getMovies(): List<Movie> {
        val response = loader.getMovies()
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}