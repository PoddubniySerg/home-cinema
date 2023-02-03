package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.Movie
import com.home.cinema.domain.models.params.page.home.GetHomeMoviesByFilterParam

interface MoviesByFilterRepository {

    fun getMoviesByFilter(param: GetHomeMoviesByFilterParam): List<Movie>?
}