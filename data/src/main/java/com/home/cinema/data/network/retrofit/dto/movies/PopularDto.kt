package com.home.cinema.data.network.retrofit.dto.movies

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PopularDto(
    @Json(name = "pagesCount") val pagesCount: Int,
    @Json(name = "films") val films: List<PopularMovieDto>
)