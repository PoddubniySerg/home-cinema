package com.home.cinema.data.repositories.page.home

import com.home.cinema.data.network.retrofit.RetrofitInstance
import com.home.cinema.data.network.retrofit.api.page.home.PopularApi
import com.home.cinema.domain.models.entities.page.home.PopularMovie
import com.home.cinema.domain.models.params.page.home.GetHomePopularParam
import com.home.cinema.domain.repositories.page.home.PopularRepository

class HomePopularRepository : PopularRepository {

    private val loader = RetrofitInstance.getRetrofit(PopularApi::class.java)

    override fun getMovies(param: GetHomePopularParam): List<PopularMovie> {
        TODO("Not yet implemented")
    }
}