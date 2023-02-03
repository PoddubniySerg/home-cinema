package com.home.cinema.data.network.retrofit.dto

import com.home.cinema.domain.models.entities.page.home.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDto(
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "genres") override val genres: List<GenreDto>?,
    @Json(name = "premiereRu") override val premiereRu: String?,
    @Json(ignore = true) override val rating: Double? = null,
    @Json(name = "kinopoiskId") val kinopoiskId: Int,
    @Json(name = "year") val year: Int?,
    @Json(name = "posterUrl") val posterUrl: String?,
    @Json(name = "countries") val countries: List<CountryDto>?,
    @Json(name = "duration") val duration: Int?
) : Movie