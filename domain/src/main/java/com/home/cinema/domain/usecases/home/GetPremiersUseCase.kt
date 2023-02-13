package com.home.cinema.domain.usecases.home

import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.entities.page.home.PremierMovie
import com.home.cinema.domain.models.params.page.home.GetHomePremiersParam
import com.home.cinema.domain.models.results.page.home.GetPremiersResult
import com.home.cinema.domain.repositories.page.home.PremiersRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.streams.toList

open class GetPremiersUseCase @Inject constructor() {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val MAX_DAYS_PREMIERS = 14L
    }

    @Inject
    protected lateinit var premiersRepository: PremiersRepository

    suspend fun execute(): GetPremiersResult {
        try {
            val dateFrom = LocalDate.now()
            val dateTo = LocalDate.from(dateFrom.plusDays(MAX_DAYS_PREMIERS))
            val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
            val movies = getPremiers(dateFrom, dateTo).stream()
                .filter { movie ->
                    val date =
                        movie.premiereRu?.let { dateString ->
                            LocalDate.parse(dateString, formatter)
                        }
                    (date != null) && (date > dateFrom) && (date < dateTo)
                }
                .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
                .toList()
            return GetPremiersResult(movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw java.lang.RuntimeException("GetPremiersUseCase exception")
        }
    }

    private suspend fun getPremiers(dateFrom: LocalDate, dateTo: LocalDate): List<PremierMovie> {
        val movies = mutableListOf<PremierMovie>()
        premiersRepository.getPremiers(
            GetHomePremiersParam(dateFrom.year, dateFrom.month.name.uppercase())
        )?.let { movies.addAll(it) }
        if (dateFrom.month != dateTo.month) {
            premiersRepository.getPremiers(
                GetHomePremiersParam(dateTo.year, dateTo.month.name.uppercase())
            )?.let { movies.addAll(it) }
        }
        return movies
    }
}