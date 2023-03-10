package com.home.cinema.domain.models.entities.filters

interface CountriesAndGenres {

    val genres: List<GenreObject>?
    val countries: List<CountryObject>?
}