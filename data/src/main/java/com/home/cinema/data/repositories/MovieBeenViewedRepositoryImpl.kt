package com.home.cinema.data.repositories

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.home.cinema.data.device.sharedpreferences.DeviceMemory
import com.home.cinema.domain.models.params.MovieBeenViewedParam
import com.home.cinema.domain.repositories.MovieBeenViewedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MovieBeenViewedRepositoryImpl : MovieBeenViewedRepository {

    private val movieBeenViewedKey = "been_viewed_movie_id: "
    private val store = DeviceMemory.getDataSource()
    private val scope = CoroutineScope(Dispatchers.Main)

    override suspend fun beenViewed(param: MovieBeenViewedParam): Boolean {
        var seen: Boolean? = null
        scope.launch {
            store.data.collect { preferences ->
                seen =
                    preferences[booleanPreferencesKey(movieBeenViewedKey + param.movieId)]
                        ?: false
                cancel()
            }
        }.join()
        return seen!!
    }
}