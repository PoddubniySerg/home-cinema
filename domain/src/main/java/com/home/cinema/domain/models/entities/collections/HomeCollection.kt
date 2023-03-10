package com.home.cinema.domain.models.entities.collections

import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.util.CollectionType

interface HomeCollection {

    val id: Int
    val type: CollectionType
    val name: String
    val count: Int
    val movies: List<Movie>
}