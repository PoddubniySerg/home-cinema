package com.home.cinema.data.device.dao.dto

import com.home.cinema.domain.models.entities.movies.GenreString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GenreStringDto(@Json(name = "genre") override val genre: String?) : GenreString