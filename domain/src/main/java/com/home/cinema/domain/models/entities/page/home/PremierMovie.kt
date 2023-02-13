package com.home.cinema.domain.models.entities.page.home

interface PremierMovie {
    val id: Int
    val posterUrlPreview: String?
    val nameRu: String?
    val nameEn: String?
    val genres: List<GenreString>?
    val rating: Double?
    val premiereRu: String?
    val isSeen: Boolean
}