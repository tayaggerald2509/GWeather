package com.exam.gweather.data.repository.weather

import com.exam.gweather.data.model.WeatherResponse
import retrofit2.Response

interface WeatherRepository {

    suspend fun fetchCurrentWeather(long: Double, lat: Double) : Response<WeatherResponse>
    suspend fun getWeathers(): List<WeatherResponse>?

}