package com.exam.gweather.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromListWeather(weather: List<Weather>?): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun toListWeather(weather: String): List<Weather> {
        val listType = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(weather, listType)
    }

    @TypeConverter
    fun fromTemperature(temperature: Temperature): String {
        return gson.toJson(temperature)
    }

    @TypeConverter
    fun toTemperature(temperature: String): Temperature {
        return gson.fromJson(temperature, Temperature::class.java)
    }

    @TypeConverter
    fun fromCountryInfo(countryInfo: CountryInfo): String {
        return gson.toJson(countryInfo)
    }

    @TypeConverter
    fun toCountryInfo(countryInfo: String): CountryInfo {
        return gson.fromJson(countryInfo, CountryInfo::class.java)
    }

}