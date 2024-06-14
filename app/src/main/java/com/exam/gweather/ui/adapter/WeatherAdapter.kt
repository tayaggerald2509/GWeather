package com.exam.gweather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.exam.gweather.R
import com.exam.gweather.data.model.Weather
import com.exam.gweather.data.model.WeatherResponse
import com.exam.gweather.utils.convertEpochSecondsToDate
import com.exam.gweather.utils.switchImageWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

class WeatherAdapter(
    private val coroutineScope: CoroutineScope,
    private val weatherList: List<WeatherResponse>
) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgWeather: AppCompatImageView = itemView.findViewById(R.id.imgWeather)
        private val txtTemperature: AppCompatTextView = itemView.findViewById(R.id.txtTemperature)

        private val txtCity: AppCompatTextView = itemView.findViewById(R.id.txtLocation)
        private val txtDate: AppCompatTextView = itemView.findViewById(R.id.txtDate)

        fun bind(weather: WeatherResponse) {
            coroutineScope.launch {
                imgWeather.setImageResource(switchImageWeather(weather.weather.first().id))
                txtTemperature.text =
                    itemView.context.getString(R.string.u2109, weather.temperature.temp.roundToInt())
                txtCity.text =
                    "${weather.city}, ${Locale("", weather.countryInfo.country).displayCountry}"

                txtDate.text = weather.dateTime.convertEpochSecondsToDate("MMM dd, yyyy hh:mm a")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather_view, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

}