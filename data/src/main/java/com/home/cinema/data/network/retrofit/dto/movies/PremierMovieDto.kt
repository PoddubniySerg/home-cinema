package com.home.cinema.data.network.retrofit.dto.movies

import com.home.cinema.data.network.retrofit.dto.parameters.CountryDto
import com.home.cinema.data.network.retrofit.dto.parameters.GenreDto
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PremierMovieDto(
    @Json(name = "kinopoiskId") override val id: Int,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "genres") override val genres: List<GenreDto>?,
    @Json(name = "premiereRu") override val premiereRu: String?,
    @Json(ignore = true) override val rating: String? = null,
    @Json(ignore = true) override var seen: Boolean = false,
    @Json(name = "year") val year: Int?,
    @Json(name = "posterUrl") override val posterUrl: String?,
    @Json(name = "countries") val countries: List<CountryDto>?,
    @Json(name = "duration") val duration: Int?
) : PremierMovie