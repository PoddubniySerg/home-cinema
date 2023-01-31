package com.home.cinema

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
//        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
//        context = this
    }
}