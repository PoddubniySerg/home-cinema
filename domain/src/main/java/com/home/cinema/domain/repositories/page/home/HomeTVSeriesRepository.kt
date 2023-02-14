package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.Movie

interface HomeTVSeriesRepository {

    suspend fun getMovies(): List<Movie>
}