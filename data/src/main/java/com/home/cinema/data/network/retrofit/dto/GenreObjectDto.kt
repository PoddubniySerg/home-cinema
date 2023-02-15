package com.home.cinema.data.network.retrofit.dto

import com.home.cinema.domain.models.entities.page.home.GenreObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GenreObjectDto(
    @Json(name = "id") override val id: Int?,
    @Json(name = "genre") override val genre: String?
) : GenreObject