package com.home.cinema.data.repositories.page.home

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.RetrofitInstance
import com.home.cinema.data.network.retrofit.api.page.home.PremiersApi
import com.home.cinema.data.repositories.mocks.page.home.MockPremiers
import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.domain.models.params.page.home.GetHomePremiersParam
import com.home.cinema.domain.repositories.page.home.PremiersRepository

class HomePremiersRepository : PremiersRepository {

    private val retrofitInstance = RetrofitInstance()

    override suspend fun getPremiers(param: GetHomePremiersParam): List<Movie> {
        val loader = retrofitInstance.getRetrofit(PremiersApi::class.java)
        val response = loader.getPremiers(param.year, param.month)
        return response.body()?.items
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
//        return MockPremiers().premiers
    }
}