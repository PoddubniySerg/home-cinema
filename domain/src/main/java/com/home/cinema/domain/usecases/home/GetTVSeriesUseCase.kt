package com.home.cinema.domain.usecases.home

import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.results.page.home.GetTop250Result
import com.home.cinema.domain.repositories.page.home.HomeTVSeriesRepository
import javax.inject.Inject
import kotlin.streams.toList

open class GetTVSeriesUseCase @Inject constructor() {

    @Inject
    protected lateinit var tvSeriesRepository: HomeTVSeriesRepository

    suspend fun execute(): GetTop250Result {
        try {
            val movies =
                tvSeriesRepository.getMovies().stream()
                    .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
                    .toList()
            return GetTop250Result(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            ex.stackTrace
            println(ex.message)
            throw java.lang.RuntimeException("GetPopularUseCase exception")
        }
    }
}