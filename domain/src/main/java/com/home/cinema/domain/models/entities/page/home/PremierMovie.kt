package com.home.cinema.domain.models.entities.page.home

import com.home.cinema.domain.models.entities.collections.movies.Movie

interface PremierMovie : Movie {
    val premiereRu: String?
}