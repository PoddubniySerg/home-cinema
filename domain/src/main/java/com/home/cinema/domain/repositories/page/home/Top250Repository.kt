package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.Movie

interface Top250Repository {

    fun getTop250(limit: Int): List<Movie>?
}