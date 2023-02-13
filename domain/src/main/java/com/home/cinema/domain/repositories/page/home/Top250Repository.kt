package com.home.cinema.domain.repositories.page.home

import com.home.cinema.domain.models.entities.page.home.PremierMovie

interface Top250Repository {

    fun getTop250(limit: Int): List<PremierMovie>?
}