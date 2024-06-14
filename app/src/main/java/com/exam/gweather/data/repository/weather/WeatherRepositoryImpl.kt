package com.exam.gweather.data.repository.weather

import android.util.Log
import com.exam.gweather.BuildConfig
import com.exam.gweather.data.model.WeatherResponse
import com.exam.gweather.data.repository.auth.AuthRepositoryImpl
import com.exam.gweather.data.source.local.WeatherDao
import com.exam.gweather.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val weatherDao: WeatherDao,
    private val authRepositoryImpl: AuthRepositoryImpl,
) : WeatherRepository {

    override suspend fun fetchCurrentWeather(long: Double, lat: Double): Response<WeatherResponse> {

        return apiService.getCurrentWeather(
            lat = lat,
            long = long,
            apiKey = BuildConfig.API_KEY,
            units = "metric"
        ).also {

            val weather = WeatherResponse(
                weather = it.body()!!.weather,
                temperature = it.body()!!.temperature,
                countryInfo = it.body()!!.countryInfo,
                city = it.body()!!.city,
                dateTime = it.body()!!.dateTime,
                email = authRepositoryImpl.getCurrentUser()!!
            )

            weatherDao.saveWeather(weather)
        }
    }

    override suspend fun getWeathers(): List<WeatherResponse>? {
        return weatherDao.getAllWeather(email = authRepositoryImpl.getCurrentUser()!!)
    }


}