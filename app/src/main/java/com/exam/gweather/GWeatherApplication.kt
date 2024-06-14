package com.exam.gweather

import android.app.Application
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.exam.gweather.data.source.WeatherDatabase
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GWeatherApplication : MultiDexApplication() {

    companion object {
        lateinit var database: WeatherDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        database = Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java, "app-database"
        ).build()
    }
}