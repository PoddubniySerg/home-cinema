package com.home.cinema.domain.usecases

import com.home.cinema.domain.models.params.page.home.MovieBeenViewedParam
import com.home.cinema.domain.models.results.page.MovieCheckBeenViewedResult
import com.home.cinema.domain.repositories.page.MovieBeenViewedRepository
import javax.inject.Inject

open class MovieCheckBeenViewedUseCase @Inject constructor() {

    @Inject
    protected lateinit var movieBeenViewedRepository: MovieBeenViewedRepository

    suspend fun execute(movieId: Int): MovieCheckBeenViewedResult {
        try {
            return MovieCheckBeenViewedResult(
                movieBeenViewedRepository.beenViewed(
                    MovieBeenViewedParam(movieId)
                )
            )
        } catch (ex: Exception) {
//            TODO handle exception
            ex.stackTrace
            println(ex.message)
            throw java.lang.RuntimeException("MovieCheckBeenViewedUseCase exception")
        }
    }
}