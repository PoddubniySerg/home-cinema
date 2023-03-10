package com.home.cinema.data.network.retrofit.dto.movies

import com.home.cinema.data.network.retrofit.dto.parameters.CountryDto
import com.home.cinema.data.network.retrofit.dto.parameters.GenreDto
import com.home.cinema.domain.models.entities.movies.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RandomCollectionMovieDto(
    @Json(name = "kinopoiskId") override val id: Int,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "genres") override val genres: List<GenreDto>?,
    @Json(name = "ratingKinopoisk") override val rating: String?,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String?,
    @Json(name = "countries") val countries: List<CountryDto>?,
    @Json(name = "posterUrl") val posterUrl: String?,
    @Json(ignore = true) override var seen: Boolean = false,
    @Json(name = "imdbId") val imdbId: String?,
    @Json(name = "nameOriginal") val nameOriginal: String?,
    @Json(name = "ratingImdb") val ratingImdb: String?,
    @Json(name = "year") val year: Int?,
    @Json(name = "type") val type: String?
) : Movie