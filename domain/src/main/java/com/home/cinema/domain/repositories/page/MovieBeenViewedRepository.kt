package com.home.cinema.domain.repositories.page

import com.home.cinema.domain.models.params.page.home.MovieBeenViewedParam

interface MovieBeenViewedRepository {

    suspend fun beenViewed(param: MovieBeenViewedParam): Boolean
}