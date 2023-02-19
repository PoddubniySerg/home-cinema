package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.collections.movies.Movie
import com.home.cinema.domain.models.params.page.home.GetHomeMoviesByFilterParam

interface RandomCollectionRepository {

    suspend fun getMoviesByFilter(param: GetHomeMoviesByFilterParam): List<Movie>?
}