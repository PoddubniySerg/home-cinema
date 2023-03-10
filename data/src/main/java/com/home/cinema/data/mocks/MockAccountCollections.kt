package com.home.cinema.data.mocks

import com.home.cinema.domain.models.entities.collections.AccountCollection

class MockAccountCollections {

    val collections = listOf<AccountCollection>(
        Collection("Favorite", 109),
        Collection("Will watch", 777),
        Collection("Русское кино", 105),
        Collection("Russian films", 4),
        Collection("Home video", 12)
    )

    inner class Collection(override val name: String, override val count: Int) : AccountCollection
}