package com.home.cinema.domain.util

sealed class CollectionType {

    object Premier : CollectionType()
    object Top250 : CollectionType()
    object Popular : CollectionType()
    object Series : CollectionType()
    class Random(val country: String, val genre: String) : CollectionType()
}