package com.home.cinema.domain.models.params.account

import com.home.cinema.domain.models.entities.movies.GenreString

class AccountSaveMovieParams(
    val posterUrlPreview: String?,
    val posterUrl: String?,
    val nameRu: String?,
    val nameEn: String?,
    val genres: List<GenreString>?,
    val rating: String?,
    var seen: Boolean
)