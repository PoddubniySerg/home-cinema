package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.CountriesAndGenres

interface CountriesAndGenresRepository {

    fun getCountriesAndGenres(): CountriesAndGenres?
}