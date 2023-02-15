package com.home.cinema.domain.models.params.page.home

class GetHomeMoviesByFilterParam(
    val country: Int,
    val genre: Int,
    val order: String?,
    val ratingFrom: Int?
)