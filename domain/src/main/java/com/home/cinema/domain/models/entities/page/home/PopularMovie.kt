package com.home.cinema.domain.models.entities.page.home

interface PopularMovie {
    val id: Int
    val posterUrlPreview: String?
    val nameRu: String?
    val nameEn: String?
    val genres: List<GenreString>?
    val rating: Double?
    val isSeen: Boolean
}