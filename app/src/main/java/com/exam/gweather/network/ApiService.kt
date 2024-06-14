package com.exam.gweather.network

import com.exam.gweather.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<WeatherResponse>

}