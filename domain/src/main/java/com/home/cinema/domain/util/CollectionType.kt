package com.home.cinema.domain.util

import com.home.cinema.domain.models.entities.filters.CountryObject
import com.home.cinema.domain.models.entities.filters.GenreObject

sealed class CollectionType {

    object Premier : CollectionType()
    object Top250 : CollectionType()
    object Popular : CollectionType()
    object Series : CollectionType()
    class Random(val country: CountryObject?, val genre: GenreObject?) : CollectionType()
}