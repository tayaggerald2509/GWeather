package com.exam.gweather.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.exam.gweather.data.model.CountryInfo
import com.exam.gweather.data.model.Temperature
import com.exam.gweather.data.model.Weather
import com.exam.gweather.data.model.WeatherResponse
import com.exam.gweather.data.repository.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherViewModel: WeatherViewModel

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var weatherObserver: Observer<WeatherResponse>

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        weatherViewModel = WeatherViewModel(weatherRepository)
        weatherViewModel.weatherResponse.observeForever(weatherObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchCurrentWeather_shouldReturnData() = runTest {
        val weatherResponse =  WeatherResponse(
            1,
            weather = arrayListOf(Weather(id = 500, main = "Cloudy", icon = "")),
            temperature = Temperature(
                temp = 31.0,
                feelsLike = 40.0,
                tempMin = 28.0,
                tempMax = 38.0,
                humidity = 72.0,
                pressure = 1015.0
            ),
            countryInfo = CountryInfo(
                id = 1,
                country = "PH",
                sunrise = 1661834187,
                sunset = 1661882248,
            ),
            dateTime = 1661870592,
            city = "Caloocan City",
            "test@example.com"
        )

        whenever(weatherRepository.fetchCurrentWeather(10.99, 44.34)).thenReturn(Response.success(weatherResponse))

        weatherViewModel.fetchCurrrentWeather(10.99, 44.34)

        verify(weatherObserver).onChanged(weatherResponse)
    }

    @Test
    fun allWeather_shouldReturnData() = runTest {

        val weatherList = listOf(

            WeatherResponse(
                1,
                weather = arrayListOf(Weather(id = 500, main = "Cloudy", icon = "")),
                temperature = Temperature(
                    temp = 31.0,
                    feelsLike = 40.0,
                    tempMin = 28.0,
                    tempMax = 38.0,
                    humidity = 72.0,
                    pressure = 1015.0
                ),
                countryInfo = CountryInfo(
                    id = 1,
                    country = "PH",
                    sunrise = 1661834187,
                    sunset = 1661882248,
                ),
                dateTime = 1661870592,
                city = "Caloocan City",
                "test@example.com"
            )
        )

        val liveData = MutableLiveData<List<WeatherResponse>>()
        liveData.value = weatherList

        whenever(weatherRepository.getWeathers()).thenReturn(liveData.value)

        weatherViewModel.allWeather();

        val observer: Observer<List<WeatherResponse>> =
            mock(Observer::class.java) as Observer<List<WeatherResponse>>
        weatherViewModel.weathers.observeForever(observer)

        // Then
        verify(observer).onChanged(weatherList)
    }

}