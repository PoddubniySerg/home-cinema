package com.home.cinema.domain.usecases

import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.entities.collections.HomeCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.MovieBeenViewedParam
import com.home.cinema.domain.models.params.home.HomeGetMoviesByFilterParam
import com.home.cinema.domain.models.params.home.HomeGetPremiersParam
import com.home.cinema.domain.models.results.HomeGetCollectionsResult
import com.home.cinema.domain.repositories.HomePageRepository
import com.home.cinema.domain.repositories.MovieBeenViewedRepository
import com.home.cinema.domain.util.CollectionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random
import kotlin.streams.toList

open class HomeGetCollectionsUseCase @Inject constructor() {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val MAX_DAYS_PREMIERS = 14L
    }

    @Inject
    protected lateinit var moviesRepository: HomePageRepository

    @Inject
    protected lateinit var movieBeenViewedRepository: MovieBeenViewedRepository

    private var counterId = 0

    suspend fun execute(names: List<String>): HomeGetCollectionsResult {
        try {
            val collections = mutableListOf<HomeCollection>()
            for (i in names.indices) {
                when (i) {
                    0 -> collections.add(getPremiers(names[i]))
                    1 -> collections.add(getPopular(names[i]))
                    2, 3 -> collections.add(getRandomCollection())
                    4 -> collections.add(getTop250(names[i]))
                    5 -> collections.add(getTvSeries(names[i]))
                }
            }
            return HomeGetCollectionsResult(collections)
        } catch (ex: Exception) {
//            TODO handle exception
            throw java.lang.RuntimeException("HomeGetCollectionsUseCase exception")
        }
    }

    private suspend fun checkMoviesSeen(movies: List<Movie>) {
        movies.forEach { movie ->
            movie.seen = movieBeenViewedRepository.beenViewed(MovieBeenViewedParam(movie.id))
        }
    }

    private suspend fun getPremiers(name: String): HomeCollection {
        val movies = getPremiers()
        checkMoviesSeen(movies)
        return HomeCollectionImpl(
            ++counterId,
            CollectionType.Premier,
            name,
            movies.size,
            movies
        )
    }

    private suspend fun getPremiers(): List<Movie> {
        val dateFrom = LocalDate.now()
        val dateTo = LocalDate.from(dateFrom.plusDays(MAX_DAYS_PREMIERS))
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

        val movies = mutableListOf<PremierMovie>()
        moviesRepository.getPremiers(
            HomeGetPremiersParam(dateFrom.year, dateFrom.month.name.uppercase())
        )?.let { movies.addAll(it) }
        if (dateFrom.month != dateTo.month) {
            moviesRepository.getPremiers(
                HomeGetPremiersParam(dateTo.year, dateTo.month.name.uppercase())
            )?.let { movies.addAll(it) }
        }

        return movies.stream()
            .filter { movie ->
                val date =
                    movie.premiereRu?.let { dateString ->
                        LocalDate.parse(dateString, formatter)
                    }
                (date != null) && (date > dateFrom) && (date < dateTo)
            }
            .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            .toList()
    }

    private suspend fun getPopular(name: String): HomeCollection {
        val movies = moviesRepository.getPopular().stream()
            .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            .toList()
        checkMoviesSeen(movies)
        return HomeCollectionImpl(
            ++counterId,
            CollectionType.Popular,
            name,
            movies.size,
            movies
        )
    }

    private suspend fun getRandomCollection(): HomeCollection {
        val countriesAndGenres = moviesRepository.getCountriesAndGenres()
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
        val type = CollectionType.Random(
            country,
            genre
        )
        val movies = moviesRepository.getMoviesByFilter(
            HomeGetMoviesByFilterParam(
                country?.id ?: 1,
                genre?.id ?: 1
            )
        )?.stream()
            ?.limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            ?.toList() ?: emptyList()
        checkMoviesSeen(movies)
        return HomeCollectionImpl(
            ++counterId,
            type,
            "${type.genre?.genre ?: "\"Unknown genre\""} ${type.country?.country ?: "Unknown country"}",
            movies.size,
            movies
        )
    }

    private suspend fun getTop250(name: String): HomeCollection {
        val movies = moviesRepository.getTop250().stream()
            .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            .toList()
        checkMoviesSeen(movies)
        return HomeCollectionImpl(
            ++counterId,
            CollectionType.Top250,
            name,
            movies.size,
            movies
        )
    }

    private suspend fun getTvSeries(name: String): HomeCollection {
        val movies = moviesRepository.getSeries().stream()
            .limit(Constants.MAX_MOVIES_COLLECTION_SIZE)
            .toList()
        checkMoviesSeen(movies)
        return HomeCollectionImpl(
            ++counterId,
            CollectionType.Series,
            name,
            movies.size,
            movies
        )
    }

    private class HomeCollectionImpl(
        override val id: Int,
        override val type: CollectionType,
        override val name: String,
        override val count: Int,
        override val movies: List<Movie>
    ) : HomeCollection
}