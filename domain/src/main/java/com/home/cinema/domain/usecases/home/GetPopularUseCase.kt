package com.home.cinema.domain.usecases.home

import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.results.page.home.GetPopularResult
import com.home.cinema.domain.repositories.page.home.PopularRepository
import javax.inject.Inject
import kotlin.streams.toList

open class GetPopularUseCase @Inject constructor() {

    @Inject
    protected lateinit var popularRepository: PopularRepository

    suspend fun execute(): GetPopularResult {
        try {
            val movies =
                popularRepository.getMovies().stream()
                    .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
                    .toList()
            return GetPopularResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            ex.stackTrace
            println(ex.message)
            throw java.lang.RuntimeException("GetPopularUseCase exception")
        }
    }
}