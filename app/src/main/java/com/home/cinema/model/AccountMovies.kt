package com.home.cinema.model

import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie

class AccountMovies(val collection: AccountCollection, val movies: List<Movie>)