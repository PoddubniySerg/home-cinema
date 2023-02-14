package com.home.cinema.domain.usecases.home

import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.results.page.home.GetTop250Result
import com.home.cinema.domain.repositories.page.home.HomeTop250Repository
import javax.inject.Inject
import kotlin.streams.toList

open class GetTop250UseCase @Inject constructor() {

    @Inject
    protected lateinit var top250Repository: HomeTop250Repository

    suspend fun execute(): GetTop250Result {
        try {
            val movies =
                top250Repository.getMovies().stream()
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