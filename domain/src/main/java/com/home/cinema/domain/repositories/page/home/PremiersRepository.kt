package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.PremierMovie
import com.home.cinema.domain.models.params.page.home.GetHomePremiersParam

interface PremiersRepository {

    suspend fun getPremiers(param: GetHomePremiersParam): List<PremierMovie>?
}