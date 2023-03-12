package com.home.cinema.domain.repositories

import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.params.account.AccountGetMoviesByCollectionIdParams
import com.home.cinema.domain.models.params.account.AccountSaveCollectionParams
import com.home.cinema.domain.models.params.account.AccountSaveMovieParams

interface AccountPageRepository {

    suspend fun getCollections(): List<AccountCollection>

    suspend fun getMoviesByCollectionId(params: AccountGetMoviesByCollectionIdParams): List<Movie>?

    suspend fun saveCollection(params: AccountSaveCollectionParams)

    suspend fun saveMovie(params: AccountSaveMovieParams)
}