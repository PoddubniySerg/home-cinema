package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.PopularMovie
import com.home.cinema.domain.models.params.page.home.GetHomePopularParam

interface PopularRepository {

    fun getMovies(param: GetHomePopularParam): List<PopularMovie>
}