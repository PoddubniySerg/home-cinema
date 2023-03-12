package com.home.cinema.domain.models.results

import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie

class AccountGetCollectionsResult(
    val collections: List<AccountCollection>,
    val moviesByCollectionId: Map<Int, List<Movie>>
)