package com.home.cinema.domain.repositories

import com.home.cinema.domain.models.params.MovieBeenViewedParam

interface MovieBeenViewedRepository {

    suspend fun beenViewed(param: MovieBeenViewedParam): Boolean
}