package com.home.cinema.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.home.cinema.data.device.dao.AppDatabase

open class DataApp : Application() {

    companion object {
        private var appContext: Context? = null
        fun getContext() = appContext!!

        private var database: AppDatabase? = null
        fun getDataBase() = database!!
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}