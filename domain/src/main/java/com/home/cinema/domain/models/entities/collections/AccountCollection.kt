package com.home.cinema.domain.models.entities.collections

import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.util.CollectionType

interface AccountCollection {

    val id: Int
    val name: String
    val count: Int
}