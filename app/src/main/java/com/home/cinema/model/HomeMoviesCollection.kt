package com.home.cinema.model

import com.home.cinema.domain.models.entities.collections.movies.Movie

class HomeMoviesCollection(
    val name: String,
    val movies: List<Movie>
)