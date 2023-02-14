package com.home.cinema.domain.models.entities.page.home

interface Movie {
    val id: Int
    val posterUrlPreview: String?
    val nameRu: String?
    val nameEn: String?
    val genres: List<GenreString>?
    val rating: String?
    var isSeen: Boolean
}