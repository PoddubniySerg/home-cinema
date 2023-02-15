package com.home.cinema.data.network.retrofit.dto.page.home

import com.home.cinema.domain.models.entities.page.home.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PopularMovieDto(
    @Json(name = "filmId") override val id: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "genres") override val genres: List<GenreDto>?,
    @Json(name = "rating") override val rating: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(ignore = true) override var seen: Boolean = false,
    @Json(name = "year") val year: Int?,
    @Json(name = "countries") val countries: List<CountryDto>?,
    @Json(name = "posterUrl") val posterUrl: String?,
    @Json(name = "filmLength") val filmLength: String?,
    @Json(name = "ratingVoteCount") val ratingVoteCount: Int?
) : Movie