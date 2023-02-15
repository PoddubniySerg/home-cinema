package com.home.cinema.data.network.retrofit.api.page.home

import com.home.cinema.data.network.retrofit.dto.page.home.RandomCollectionDto
import com.home.cinema.data.secret.Secret
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RandomCollectionsApi {

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films")
    suspend fun getMovies(
        @Query(value = "countries") country: Int,
        @Query(value = "genres") genre: Int
    ): Response<RandomCollectionDto>
}