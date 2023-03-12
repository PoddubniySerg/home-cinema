package com.home.cinema.data.device.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.home.cinema.data.device.dao.dto.AccountCollectionDto
import com.home.cinema.data.device.dao.dto.AccountCollectionMovieCrossRef
import com.home.cinema.data.device.dao.dto.AccountMovieDto

@Database(
    entities = [AccountCollectionDto::class, AccountMovieDto::class, AccountCollectionMovieCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountPageDao(): AccountPageDao
}