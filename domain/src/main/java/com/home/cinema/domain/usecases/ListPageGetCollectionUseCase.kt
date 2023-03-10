package com.home.cinema.domain.usecases

import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.page.listpage.GetListPagePremiersParam
import com.home.cinema.domain.models.params.page.listpage.GetPopularByPageParam
import com.home.cinema.domain.models.results.page.listpage.GetMoviesByPageResult
import com.home.cinema.domain.models.results.page.listpage.GetPremiersListPageResult
import com.home.cinema.domain.repositories.page.ListPageRepository
import java.time.LocalDate
import javax.inject.Inject

open class ListPageGetCollectionUseCase @Inject constructor() {

    companion object {
        private const val MAX_DAYS_PREMIERS = 14L
    }

    @Inject
    protected lateinit var moviesRepository: ListPageRepository

    suspend fun getPremiers(): GetPremiersListPageResult {
        try {
            val dateFrom = LocalDate.now()
            val dateTo = LocalDate.from(dateFrom.plusDays(MAX_DAYS_PREMIERS))
            val movies = getPremiers(dateFrom, dateTo)
            return GetPremiersListPageResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw java.lang.RuntimeException("GetPremiersListPageUseCase exception")
        }
    }

    suspend fun getMovies(page: Int): GetMoviesByPageResult {
        try {
            return GetMoviesByPageResult(
                moviesRepository.getPopular(
                    GetPopularByPageParam(page)
                )
            )
        } catch (ex: Exception) {
//            TODO handle exception
            ex.stackTrace
            println(ex.message)
            throw java.lang.RuntimeException("GetMoviesByPageUseCase exception")
        }
    }

    private suspend fun getPremiers(dateFrom: LocalDate, dateTo: LocalDate): List<PremierMovie> {
        val movies = mutableListOf<PremierMovie>()
        moviesRepository.getPremiers(
            GetListPagePremiersParam(dateFrom.year, dateFrom.month.name.uppercase())
        )?.let { movies.addAll(it) }
        if (dateFrom.month != dateTo.month) {
            moviesRepository.getPremiers(
                GetListPagePremiersParam(dateTo.year, dateTo.month.name.uppercase())
            )?.let { movies.addAll(it) }
        }
        return movies
    }
}