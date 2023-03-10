package com.home.cinema.data.network.retrofit.dto.parameters

import com.home.cinema.domain.models.entities.filters.CountryObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CountryObjectDto(
    @Json(name = "id") override val id: Int?,
    @Json(name = "country") override val country: String?
) : CountryObject