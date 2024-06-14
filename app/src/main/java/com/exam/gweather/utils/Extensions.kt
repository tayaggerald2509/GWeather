package com.exam.gweather.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat

import androidx.core.util.Pair
import com.exam.gweather.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Extension function to validate email
fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

inline fun <reified T> Context.startActivity(
    bundle: Bundle? = null,
    vararg sharedElemets: Pair<View, String>
) {
    val intent = Intent(this, T::class.java)
    bundle?.let {
        intent.putExtras(it)
    }

    if (sharedElemets.isNotEmpty()) {
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity, *sharedElemets)
        startActivity(intent, options.toBundle())
    } else {
        startActivity(intent)
    }
}


suspend inline fun Long.convertEpochSecondsToDate(
    dateFormat: String,
    locale: Locale = Locale.getDefault()
): String {
    return withContext(Dispatchers.Default) {
        val sdf = SimpleDateFormat(dateFormat, locale)
        val date = Date(this@convertEpochSecondsToDate * 1000)
        sdf.format(date)
    }
}


fun isNightTime(): Boolean {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    return hour < 6 || hour >= 18 // Define night time as 6 PM to 6 AM
}


fun switchImageWeather(code: Int): Int {
    return when {
        isNightTime() -> R.drawable.night_icon
        code <= 232 -> R.drawable.cloud_heavy_rain_rain_storm_thunderbolt_icon
        code <= 321 -> R.drawable.cloud_heavy_rain_rain_weather_icon
        code <= 500 -> R.drawable.cloud_rain_weather_icon
        code == 800 -> R.drawable.hot_sun_weather_icon
        code > 800 -> R.drawable.cloud_sun_sunny_weather_icon
        else -> R.drawable.hot_summer_sun_umbrella_weather_icon
    }
}