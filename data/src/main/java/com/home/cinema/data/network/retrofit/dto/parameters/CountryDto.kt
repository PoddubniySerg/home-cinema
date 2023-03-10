package com.home.cinema.data.network.retrofit.dto.parameters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CountryDto(@Json(name = "country") val country: String)