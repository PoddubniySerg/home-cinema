package com.home.cinema.data.network.retrofit.dto.page.home

import com.home.cinema.domain.models.entities.collections.movies.GenreString
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GenreDto(@Json(name = "genre") override val genre: String) : GenreString