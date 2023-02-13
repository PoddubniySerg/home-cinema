package com.home.cinema.data.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun <T> getRetrofit(className: Class<T>): T {
        return retrofit.create(className)
    }
}