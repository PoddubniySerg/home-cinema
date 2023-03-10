package com.home.cinema.domain.models.entities.movies

interface Movie {
    val id: Int
    val posterUrlPreview: String?
    val posterUrl: String?
    val nameRu: String?
    val nameEn: String?
    val genres: List<GenreString>?
    val rating: String?
    var seen: Boolean
}