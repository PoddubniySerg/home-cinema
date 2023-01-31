package com.home.cinema

import com.home.cinema.data.DataApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : DataApp() {

    companion object {
//        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
//        context = this
    }
}