package com.home.cinema.data.network.retrofit.api.page

import com.home.cinema.data.network.retrofit.dto.movies.PopularDto
import com.home.cinema.data.network.retrofit.dto.movies.PremiersDto
import com.home.cinema.data.secret.Secret
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ListPageApi {

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremiers(
        @Query(value = "year") year: Int,
        @Query(value = "month") month: String
    ): Response<PremiersDto>

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopular(
        @Query(value = "page") page: Int
    ): Response<PopularDto>
}