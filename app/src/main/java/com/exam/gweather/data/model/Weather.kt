package com.exam.gweather.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("icon") val icon: String,
) : Serializable

data class Temperature(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
)

data class CountryInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
)

@Entity(tableName = "weather")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("weather") var weather: List<Weather>,
    @SerializedName("main") var temperature: Temperature,
    @SerializedName("sys") var countryInfo: CountryInfo,
    @SerializedName("dt") var dateTime: Long,
    @SerializedName("name") var city: String,
    var email: String
)