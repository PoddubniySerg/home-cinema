package com.home.cinema.domain.usecases

import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.MovieBeenViewedParam
import com.home.cinema.domain.models.params.listpage.*
import com.home.cinema.domain.models.results.GetMoviesByPageResult
import com.home.cinema.domain.repositories.ListPageRepository
import com.home.cinema.domain.repositories.MovieBeenViewedRepository
import com.home.cinema.domain.util.CollectionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

open class ListPageGetCollectionUseCase @Inject constructor() {

    companion object {
        private const val MAX_DAYS_PREMIERS = 14L
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }

    @Inject
    protected lateinit var moviesRepository: ListPageRepository

    @Inject
    protected lateinit var movieBeenViewedRepository: MovieBeenViewedRepository

    suspend fun execute(page: Int, collectionType: CollectionType): GetMoviesByPageResult {
        return when (collectionType) {
            CollectionType.Popular -> getPopular(page)
            CollectionType.Premier -> getPremiers(page)
            CollectionType.Series -> getSeries(page)
            CollectionType.Top250 -> getTop250(page)
            is CollectionType.Random -> getMoviesByFilter(page, collectionType)
        }
    }

    private suspend fun checkMoviesSeen(movies: List<Movie>) {
        movies.forEach { movie ->
            movie.seen = movieBeenViewedRepository.beenViewed(MovieBeenViewedParam(movie.id))
        }
    }

    private suspend fun getPopular(page: Int): GetMoviesByPageResult {
        try {
            val movies =
                moviesRepository.getPopular(
                    ListPageGetPopularByPageParam(page)
                )
            movies?.let { checkMoviesSeen(it) }
            return GetMoviesByPageResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw RuntimeException("GetMoviesByPageUseCase exception")
        }
    }

    private suspend fun getPremiers(page: Int): GetMoviesByPageResult {
        try {
            val dateFrom = LocalDate.now()
            val dateTo = LocalDate.from(dateFrom.plusDays(MAX_DAYS_PREMIERS))
            val movies = getPremiers(dateFrom, dateTo, page)
            checkMoviesSeen(movies)
            return GetMoviesByPageResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw RuntimeException("GetPremiersListPageUseCase exception")
        }
    }

    private suspend fun getSeries(page: Int): GetMoviesByPageResult {
        try {
            val movies =
                moviesRepository.getSeries(
                    ListPageGetSeriesParam(page)
                )
            movies?.let { checkMoviesSeen(it) }
            return GetMoviesByPageResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw RuntimeException("GetMoviesByPageUseCase exception")
        }
    }

    private suspend fun getTop250(page: Int): GetMoviesByPageResult {
        try {
            val movies =
                moviesRepository.getTop250(
                    ListPageGetTop250Param(page)
                )
            movies?.let { checkMoviesSeen(it) }
            return GetMoviesByPageResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw RuntimeException("GetMoviesByPageUseCase exception")
        }
    }

    private suspend fun getMoviesByFilter(
        page: Int,
        type: CollectionType.Random
    ): GetMoviesByPageResult {
        try {
            val movies =
                moviesRepository.getMoviesByFilter(
                    ListPageGetMoviesByFilterParam(
                        type.country?.id ?: throw RuntimeException("Country is null"),
                        type.genre?.id ?: throw RuntimeException("Genre is null"),
                        page
                    )
                )
            movies?.let { checkMoviesSeen(it) }
            return GetMoviesByPageResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw RuntimeException("GetMoviesByPageUseCase exception")
        }
    }

    private suspend fun getPremiers(
        dateFrom: LocalDate,
        dateTo: LocalDate,
        page: Int
    ): List<PremierMovie> {
        val movies = mutableListOf<PremierMovie>()
        moviesRepository.getPremiers(
            ListPageGetPremiersParam(dateFrom.year, dateFrom.month.name.uppercase(), page)
        )?.let { movies.addAll(it) }
        if (dateFrom.month != dateTo.month) {
            moviesRepository.getPremiers(
                ListPageGetPremiersParam(dateTo.year, dateTo.month.name.uppercase(), page)
            )?.let { movies.addAll(it) }
        }
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return movies.filter { movie ->
            val date =
                movie.premiereRu?.let { dateString ->
                    LocalDate.parse(dateString, formatter)
                }
            (date != null) && (date > dateFrom) && (date < dateTo)
        }
    }
}