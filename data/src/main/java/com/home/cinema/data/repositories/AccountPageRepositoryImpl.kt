package com.home.cinema.data.repositories

import com.home.cinema.data.DataApp
import com.home.cinema.data.device.dao.AccountPageDao
import com.home.cinema.data.device.dao.dto.AccountCollectionDto
import com.home.cinema.data.device.dao.dto.AccountMovieDto
import com.home.cinema.data.device.dao.dto.GenreStringDto
import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.GenreString
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.params.account.AccountGetMoviesByCollectionIdParams
import com.home.cinema.domain.models.params.account.AccountSaveCollectionParams
import com.home.cinema.domain.models.params.account.AccountSaveMovieParams
import com.home.cinema.domain.repositories.AccountPageRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class AccountPageRepositoryImpl : AccountPageRepository {

    private val accountPageDao: AccountPageDao = DataApp.getDataBase().accountPageDao()

    override suspend fun getCollections(): List<AccountCollection> {
        return accountPageDao.getCollections()
    }

    override suspend fun getMoviesByCollectionId(params: AccountGetMoviesByCollectionIdParams): List<Movie> {
        val types = Types.newParameterizedType(List::class.java, GenreStringDto::class.java)
        val parser = Moshi.Builder().build().adapter<List<GenreStringDto>>(types)
        return if (params.limit == null) {
            accountPageDao
                .getAccountCollectionWithMovies(params.collectionId)
                .movies
                .map { convertToMovie(it, parser) }
        } else {
            accountPageDao.getAccountCollectionWithMoviesLimit(
                params.collectionId,
                params.limit!!
            )
                .movies
                .map { convertToMovie(it, parser) }
        }
    }

    override suspend fun saveCollection(params: AccountSaveCollectionParams) {
        val collectionIsExist = accountPageDao.getCountCollectionByName(params.name) > 0
        if (!collectionIsExist) {
            val defaultId = 0
            val defaultCount = 0
            val collection = AccountCollectionDto(defaultId, params.name, defaultCount)
            accountPageDao.saveCollection(collection)
        }
    }

    override suspend fun saveMovie(params: AccountSaveMovieParams) {
        val movieIsExist =
            accountPageDao.getCountMovieByName(params.nameRu ?: "", params.nameEn ?: "") > 0
        val types = Types.newParameterizedType(List::class.java, GenreStringDto::class.java)
        val parser = Moshi.Builder().build().adapter<List<GenreStringDto>>(types)
        val genres = params.genres?.map { GenreStringDto(it.genre) }
        val defaultId = 0
        val movie =
            AccountMovieDto(
                defaultId,
                params.posterUrlPreview,
                params.nameRu,
                params.nameEn,
                parser.toJson(genres),
                params.rating,
                params.seen,
                params.posterUrl
            )
        if (!movieIsExist) {
            accountPageDao.saveMovie(movie)
        } else {
            accountPageDao.updateMovie(movie)
        }
    }

    private fun convertToMovie(
        movie: AccountMovieDto,
        parser: JsonAdapter<List<GenreStringDto>>
    ): Movie {
        return object : Movie {
            override val id: Int
                get() = movie.id
            override val posterUrlPreview: String?
                get() = movie.posterUrlPreview
            override val posterUrl: String?
                get() = movie.posterUrl
            override val nameRu: String?
                get() = movie.nameRu
            override val nameEn: String?
                get() = movie.nameEn
            override val genres: List<GenreString>?
                get() = parser.fromJson(movie.genres ?: "[]")
            override val rating: String?
                get() = movie.rating
            override var seen: Boolean = false
                get() = movie.seen
                set(value) {
                    field = value
                }

        }
    }
}