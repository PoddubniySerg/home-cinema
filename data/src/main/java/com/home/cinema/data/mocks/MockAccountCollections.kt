package com.home.cinema.data.mocks

import com.home.cinema.domain.models.entities.collections.AccountCollection

class MockAccountCollections {

    val collections = listOf<AccountCollection>(
        Collection("Favorite", 109, 1),
        Collection("Will watch", 777, 2),
        Collection("Русское кино", 105, 3),
        Collection("Russian films", 4, 4),
        Collection("Home video", 12, 5)
    )

    private class Collection(
        override val name: String,
        override val count: Int,
        override val id: Int
    ) : AccountCollection
}