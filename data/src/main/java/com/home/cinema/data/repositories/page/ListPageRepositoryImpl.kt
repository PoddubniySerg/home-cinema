package com.home.cinema.data.repositories.page

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.MoviesSource
import com.home.cinema.data.network.retrofit.api.page.ListPageApi
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.page.listpage.GetListPagePremiersParam
import com.home.cinema.domain.models.params.page.listpage.GetPopularByPageParam
import com.home.cinema.domain.repositories.page.ListPageRepository

class ListPageRepositoryImpl : ListPageRepository {

    private val loader = MoviesSource().getLoader(ListPageApi::class.java)

    override suspend fun getPremiers(param: GetListPagePremiersParam): List<PremierMovie> {
        val response = loader.getPremiers(param.year, param.month)
        return response.body()?.items
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }

    override suspend fun getPopular(param: GetPopularByPageParam): List<Movie> {
        val response = loader.getPopular(param.pageNumber)
        return response.body()?.films
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}