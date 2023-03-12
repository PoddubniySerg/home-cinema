package com.home.cinema.data.device.dao

import androidx.room.*
import com.home.cinema.data.device.dao.dto.AccountCollectionDto
import com.home.cinema.data.device.dao.dto.AccountCollectionMovieCrossRef
import com.home.cinema.data.device.dao.dto.AccountCollectionWithMoviesDto
import com.home.cinema.data.device.dao.dto.AccountMovieDto

@Dao
interface AccountPageDao {

    @Query("SELECT * FROM collections")
    fun getCollections(): List<AccountCollectionDto>

    @Query("SELECT COUNT() FROM collections WHERE name = :name")
    fun getCountCollectionByName(name: String): Int

    @Query("SELECT COUNT() FROM movies WHERE name_ru = :nameRu AND name_en = :nameEn")
    fun getCountMovieByName(nameRu: String, nameEn: String): Int

    @Transaction
    @Query("SELECT * FROM collections WHERE collection_id = :id")
    fun getAccountCollectionWithMovies(id: Int): AccountCollectionWithMoviesDto

    @Transaction
    @Query("SELECT * FROM collections WHERE collection_id = :id LIMIT :limit")
    fun getAccountCollectionWithMoviesLimit(id: Int, limit: Int): AccountCollectionWithMoviesDto

    @Query("SELECT COUNT() FROM collection_movie_cross_ref WHERE movie_id = :movieId")
    fun getCountCollectionsForMovie(movieId: Int): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveCollection(collectionDto: AccountCollectionDto)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveMovie(movieDto: AccountMovieDto)

    @Update
    fun updateMovie(movieDto: AccountMovieDto)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun saveCrossRef(crossRef: AccountCollectionMovieCrossRef)

    @Query("DELETE FROM collections WHERE collection_id = :id")
    fun removeCollection(id: Int)

    @Query("DELETE FROM movies WHERE movie_id = :id")
    fun removeMovie(id: Int)

    @Query("DELETE FROM collection_movie_cross_ref WHERE collection_id = :collectionId AND movie_id = :movieId")
    fun remove(collectionId: Int, movieId: Int)
}