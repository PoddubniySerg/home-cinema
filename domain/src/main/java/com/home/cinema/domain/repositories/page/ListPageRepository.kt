package com.home.cinema.domain.repositories.page

import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.entities.movies.PremierMovie
import com.home.cinema.domain.models.params.page.listpage.GetListPagePremiersParam
import com.home.cinema.domain.models.params.page.listpage.GetPopularByPageParam

interface ListPageRepository {

    suspend fun getPremiers(param: GetListPagePremiersParam): List<PremierMovie>?

    suspend fun getPopular(param: GetPopularByPageParam): List<Movie>
}