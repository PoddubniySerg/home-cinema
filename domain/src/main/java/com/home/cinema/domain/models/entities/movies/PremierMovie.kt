package com.home.cinema.domain.models.entities.movies

import com.home.cinema.domain.models.entities.movies.Movie

interface PremierMovie : Movie {
    val premiereRu: String?
}