package com.exam.gweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.gweather.data.model.WeatherResponse
import com.exam.gweather.data.repository.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse> get() = _weatherResponse

    private val _weathers = MutableLiveData<List<WeatherResponse>>()
    val weathers: LiveData<List<WeatherResponse>> = _weathers

    init {
        allWeather()
    }

    fun fetchCurrrentWeather(long: Double, lat: Double) {
        viewModelScope.launch {
            try {

                val response = weatherRepository.fetchCurrentWeather(
                    lat = lat,
                    long = long,
                )

                if (response.isSuccessful) {
                    _weatherResponse.postValue(response.body())
                }
            } catch (e: Exception) {
//                e.printStackTrace()
            }
        }
    }

    fun allWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = weatherRepository.getWeathers()
                _weathers.postValue(response!!)
            }catch (e: Exception) {
//                e.printStackTrace()
            }
        }
    }
}