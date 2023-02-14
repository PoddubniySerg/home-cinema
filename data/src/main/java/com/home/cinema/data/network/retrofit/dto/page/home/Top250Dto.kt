package com.home.cinema.data.network.retrofit.dto.page.home

import com.home.cinema.domain.models.entities.page.home.PremierMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Top250Dto(
    @Json(name = "pagesCount") val pagesCount: Int?,
    @Json(name = "films") val films: List<Top250MovieDto>?
)