package com.home.cinema.data.network.retrofit.dto.page.home

import com.home.cinema.domain.models.entities.page.home.PremierMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TVSeriesDto(
    @Json(name = "total") val total: Int,
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "items") val films: List<TVSeriesMovieDto>
)