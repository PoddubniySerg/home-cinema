package com.home.cinema.domain.repositories.page

import com.home.cinema.domain.models.entities.page.home.CountriesAndGenres

interface CountriesAndGenresRepository {

    suspend fun getCountriesAndGenres(): CountriesAndGenres
}