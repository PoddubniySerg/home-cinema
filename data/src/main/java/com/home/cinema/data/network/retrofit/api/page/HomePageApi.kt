package com.home.cinema.data.network.retrofit.api.page

import com.home.cinema.data.network.retrofit.dto.parameters.CountryAndGenresDto
import com.home.cinema.data.network.retrofit.dto.movies.*
import com.home.cinema.data.secret.Secret
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface HomePageApi {

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films/filters")
    suspend fun getCountriesAndGenres(): Response<CountryAndGenresDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremiers(
        @Query(value = "year") year: Int,
        @Query(value = "month") month: String
    ): Response<PremiersDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getPopular(
    ): Response<PopularDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films")
    suspend fun getByFilters(
        @Query(value = "countries") country: Int,
        @Query(value = "genres") genre: Int
    ): Response<RandomCollectionDto>

    @Headers("X-API-KEY: ${Secret.API_KEY}")
    @GET("/api/v2.2/films/top")
    suspend fun getTop250(
    ): Response<Top250Dto>

    @Headers(
        "X-API-KEY: ${Secret.API_KEY}"
    )
    @GET("/api/v2.2/films?type=TV_SERIES")
    suspend fun getTvSeries(
    ): Response<TVSeriesDto>
}