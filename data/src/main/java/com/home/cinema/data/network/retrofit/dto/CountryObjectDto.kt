package com.home.cinema.data.network.retrofit.dto

import com.home.cinema.domain.models.entities.page.home.CountryObject
import com.home.cinema.domain.models.entities.page.home.GenreObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CountryObjectDto(
    @Json(name = "id") override val id: Int?,
    @Json(name = "country") override val country: String?
) : CountryObject