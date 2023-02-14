package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.Movie

interface HomeTop250Repository {

    suspend fun getMovies(): List<Movie>
}