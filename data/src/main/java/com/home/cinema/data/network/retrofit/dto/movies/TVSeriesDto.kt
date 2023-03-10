package com.home.cinema.data.network.retrofit.dto.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TVSeriesDto(
    @Json(name = "total") val total: Int,
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "items") val films: List<TVSeriesMovieDto>
)