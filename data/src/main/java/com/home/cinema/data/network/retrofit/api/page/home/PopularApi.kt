package com.home.cinema.data.network.retrofit.api.page.home

import com.home.cinema.data.network.retrofit.dto.page.home.PopularDto
import com.home.cinema.data.secret.Secret
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PopularApi {

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getMovies(
    ): Response<PopularDto>
}