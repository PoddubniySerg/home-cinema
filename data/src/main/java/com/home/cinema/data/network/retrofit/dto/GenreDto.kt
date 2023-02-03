package com.home.cinema.data.network.retrofit.dto

import com.home.cinema.domain.models.entities.page.home.GenreString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GenreDto(@Json(name = "genre") override val genre: String) : GenreString