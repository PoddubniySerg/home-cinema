package com.home.cinema.data.page.home

import com.home.cinema.data.network.retrofit.RetrofitInstance
import com.home.cinema.data.network.retrofit.api.home.page.PremiersApi
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.domain.models.params.page.home.GetHomePremiersParam
import com.home.cinema.domain.repositories.page.home.PremiersRepository

class HomePremiersRepository : PremiersRepository {

    private val retrofitInstance = RetrofitInstance()

    override suspend fun getPremiers(param: GetHomePremiersParam): List<Movie>? {
        val loader = retrofitInstance.getRetrofit(PremiersApi::class.java)
        val response = loader.getPremiers(param.year, param.month)
        println(response.body()?.items)
        println(response)
        return response.body()?.items
    }
}