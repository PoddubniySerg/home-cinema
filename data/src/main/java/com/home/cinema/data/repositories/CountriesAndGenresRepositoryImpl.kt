package com.home.cinema.data.repositories

import com.home.cinema.data.exceptions.NetworkResponseException
import com.home.cinema.data.network.retrofit.NetworkStore
import com.home.cinema.data.network.retrofit.api.page.home.CountriesAndGenresApi
import com.home.cinema.domain.models.entities.page.home.CountriesAndGenres
import com.home.cinema.domain.repositories.page.CountriesAndGenresRepository

class CountriesAndGenresRepositoryImpl : CountriesAndGenresRepository {

    private val loader = NetworkStore.getLoader(CountriesAndGenresApi::class.java)

    override suspend fun getCountriesAndGenres(): CountriesAndGenres {
        val response = loader.getCountriesAndGenres()
        return response.body()
            ?: throw NetworkResponseException("Code:${response.code()}, message: ${response.message()}")
    }
}