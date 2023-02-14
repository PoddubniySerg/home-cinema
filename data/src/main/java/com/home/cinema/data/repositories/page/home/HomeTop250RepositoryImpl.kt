package com.home.cinema.data.repositories.page.home

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.NetworkStore
import com.home.cinema.data.network.retrofit.api.page.home.HomeTop250Api
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.domain.repositories.page.home.HomeTop250Repository

class HomeTop250RepositoryImpl : HomeTop250Repository {

    private val loader = NetworkStore.getLoader(HomeTop250Api::class.java)

    override suspend fun getMovies(): List<Movie> {
        val response = loader.getMovies()
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}