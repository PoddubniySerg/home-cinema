package com.home.cinema.model

import com.home.cinema.domain.models.entities.page.home.PremierMovie

class HomeMoviesCollection(
    val name: String,
    val movies: List<PremierMovie>
)