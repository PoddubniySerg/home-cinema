package com.home.cinema.data.network.retrofit

import com.home.cinema.data.DataApp
import com.home.cinema.data.R
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MoviesSource {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(DataApp.getContext().resources.getString(R.string.kinopoisk_base_url))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    fun <T> getLoader(className: Class<T>): T {
        return retrofit.create(className)
    }
}