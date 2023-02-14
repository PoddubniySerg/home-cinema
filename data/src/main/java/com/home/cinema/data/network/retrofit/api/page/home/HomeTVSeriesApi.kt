package com.home.cinema.data.network.retrofit.api.page.home

import com.home.cinema.data.network.retrofit.dto.page.home.TVSeriesDto
import com.home.cinema.data.secret.Secret
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface HomeTVSeriesApi {

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films?type=TV_SERIES")
    suspend fun getMovies(
    ): Response<TVSeriesDto>
}