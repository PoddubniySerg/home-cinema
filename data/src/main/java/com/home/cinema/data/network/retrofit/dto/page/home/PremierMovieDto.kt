package com.home.cinema.data.network.retrofit.dto.page.home

import com.home.cinema.domain.models.entities.page.home.PremierMovie
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
    @Json(ignore = true) override val rating: Double? = null,
    @Json(ignore = true) override val isSeen: Boolean = false,
    @Json(name = "year") val year: Int?,
    @Json(name = "posterUrl") val posterUrl: String?,
    @Json(name = "countries") val countries: List<CountryDto>?,
    @Json(name = "duration") val duration: Int?
) : PremierMovie