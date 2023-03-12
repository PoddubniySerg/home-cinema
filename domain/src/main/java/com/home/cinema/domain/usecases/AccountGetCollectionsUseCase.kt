package com.home.cinema.domain.usecases

import com.home.cinema.domain.constants.Constants
import com.home.cinema.domain.models.entities.collections.AccountCollection
import com.home.cinema.domain.models.entities.movies.Movie
import com.home.cinema.domain.models.params.account.AccountGetMoviesByCollectionIdParams
import com.home.cinema.domain.models.params.account.AccountSaveCollectionParams
import com.home.cinema.domain.models.results.AccountGetCollectionsResult
import com.home.cinema.domain.repositories.AccountPageRepository
import javax.inject.Inject

open class AccountGetCollectionsUseCase @Inject constructor() {

    @Inject
    protected lateinit var accountPageRepository: AccountPageRepository

    suspend fun execute(): AccountGetCollectionsResult {
        try {
            val collections = checkRequired()
            val movies = mutableMapOf<Int, List<Movie>>()
            collections.forEach { collection ->
                if (
                    collection.name == Constants.ACCOUNT_SEEN_COLLECTION_KEY
                    || collection.name == Constants.ACCOUNT_INTERESTED_COLLECTION_KEY
                ) {
                    movies[collection.id] =
                        accountPageRepository.getMoviesByCollectionId(
                            AccountGetMoviesByCollectionIdParams(
                                collection.id,
                                Constants.MAX_MOVIES_COLLECTION_SIZE.toInt()
                            )
                        ) ?: emptyList()
                }
            }
            return AccountGetCollectionsResult(collections, movies)
        } catch (ex: Exception) {
//            TODO handle exception
            throw java.lang.RuntimeException("AccountGetCollectionsUseCase exception")
        }
    }

    private suspend fun checkRequired(): List<AccountCollection> {
        var collection = accountPageRepository.getCollections()
        if (collection.isEmpty()) {
            initCollections()
            collection = accountPageRepository.getCollections()
        }
        val names = collection.map { it.name }
        var collectionIsValid = true
        collection.map { it.name }.forEach { name ->
            if (!checkCollectionName(name, names)) {
                collectionIsValid = false
            }
        }
        return if (collectionIsValid) {
            collection
        } else {
            accountPageRepository.getCollections()
        }
    }

    private suspend fun initCollections() {
        val names = listOf(
            Constants.ACCOUNT_SEEN_COLLECTION_KEY,
            Constants.ACCOUNT_FAVORITE_COLLECTION_KEY,
            Constants.ACCOUNT_WILL_VIEW_COLLECTION_KEY,
            Constants.ACCOUNT_INTERESTED_COLLECTION_KEY
        )
        names.forEach { accountPageRepository.saveCollection(AccountSaveCollectionParams(it)) }
    }

    private suspend fun checkCollectionName(name: String, names: List<String>): Boolean {
        return if (!names.contains(name)) {
            accountPageRepository.saveCollection(AccountSaveCollectionParams(name))
            false
        } else {
            true
        }
    }
}