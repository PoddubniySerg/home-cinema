package com.home.cinema.domain.models.params.page.home

class GetHomeMoviesByFilterParam(
    val countries: List<Int>?,
    val genres: List<Int>?,
    val order: String?,
    val ratingFrom: Int?
)