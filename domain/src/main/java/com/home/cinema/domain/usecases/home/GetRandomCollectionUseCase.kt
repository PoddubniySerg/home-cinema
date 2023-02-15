package com.home.cinema.domain.usecases.home

import com.home.cinema.domain.models.params.page.home.GetHomeMoviesByFilterParam
import com.home.cinema.domain.models.results.page.home.GetRandomCollectionResult
import com.home.cinema.domain.repositories.page.CountriesAndGenresRepository
import com.home.cinema.domain.repositories.page.home.RandomCollectionRepository
import javax.inject.Inject
import kotlin.random.Random

open class GetRandomCollectionUseCase @Inject constructor() {

    @Inject
    protected lateinit var countriesAndGenresRepository: CountriesAndGenresRepository

    @Inject
    protected lateinit var randomCollectionRepository: RandomCollectionRepository

    suspend fun execute(): GetRandomCollectionResult {
        try {
            val countriesAndGenres = countriesAndGenresRepository.getCountriesAndGenres()
            val country = countriesAndGenres.countries?.get(
                Random.nextInt(
                    0,
                    countriesAndGenres.countries?.size ?: 0
                )
            )

            val genre = countriesAndGenres.genres?.get(
                Random.nextInt(
                    0,
                    countriesAndGenres.genres?.size ?: 0
                )
            )

            return GetRandomCollectionResult(
                "${genre?.genre ?: "Unknown genre"} ${country?.country ?: "Unknown country"}",
                randomCollectionRepository.getMoviesByFilter(
                    GetHomeMoviesByFilterParam(
                        country?.id ?: 1,
                        genre?.id ?: 1,
                        null,
                        null
                    )
                )
            )
        } catch (ex: Exception) {
//            TODO handle exception
            ex.stackTrace
            println(ex.message)
            throw java.lang.RuntimeException("GetRandomCollectionUseCase exception")
        }
    }
}