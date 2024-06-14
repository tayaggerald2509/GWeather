package com.exam.gweather.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exam.gweather.data.model.Weather
import com.exam.gweather.data.model.WeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE email = :email ORDER BY id DESC")
    suspend fun getAllWeather(email: String): List<WeatherResponse>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherResponse)
}