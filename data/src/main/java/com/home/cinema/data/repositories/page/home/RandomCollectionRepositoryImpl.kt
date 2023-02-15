package com.home.cinema.data.repositories.page.home

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.NetworkStore
import com.home.cinema.data.network.retrofit.api.page.home.RandomCollectionsApi
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.domain.models.params.page.home.GetHomeMoviesByFilterParam
import com.home.cinema.domain.repositories.page.home.RandomCollectionRepository

class RandomCollectionRepositoryImpl : RandomCollectionRepository {

    private val loader = NetworkStore.getLoader(RandomCollectionsApi::class.java)

    override suspend fun getMoviesByFilter(param: GetHomeMoviesByFilterParam): List<Movie> {
        val response = loader.getMovies(param.country, param.genre)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}