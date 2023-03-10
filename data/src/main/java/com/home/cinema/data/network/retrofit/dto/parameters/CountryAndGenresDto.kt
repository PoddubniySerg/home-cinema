package com.home.cinema.data.network.retrofit.dto.parameters

import com.home.cinema.domain.models.entities.filters.CountriesAndGenres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CountryAndGenresDto(
    @Json(name = "genres") override val genres: List<GenreObjectDto>,
    @Json(name = "countries") override val countries: List<CountryObjectDto>
) : CountriesAndGenres