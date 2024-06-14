package com.exam.gweather.ui.home

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.exam.gweather.R
import com.exam.gweather.databinding.FragmentHomeBinding
import com.exam.gweather.ui.WeatherViewModel
import com.exam.gweather.utils.convertEpochSecondsToDate
import com.exam.gweather.utils.isNightTime
import com.exam.gweather.utils.switchImageWeather
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver();
    }

    private fun initObserver() {
        weatherViewModel.weatherResponse.observe(requireActivity()) {

            weatherViewModel.viewModelScope.launch {
                binding.txtLocation.text = "${it.city}\n${Locale("", it.countryInfo.country).displayCountry}"
                binding.txtTemperature.text = getString(R.string.u2109, it.temperature.temp.roundToInt())
                binding.txtSunrise.text =
                    it.countryInfo.sunrise.convertEpochSecondsToDate("hh:mm a")
                binding.txtSunset.text = it.countryInfo.sunset.convertEpochSecondsToDate("hh:mm a")

                binding.imgWeather.setImageResource(switchImageWeather(it.weather.first().id))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Method to receive the location from the Activity
    fun updateLocation(location: Location) {
        weatherViewModel.fetchCurrrentWeather(long = location.longitude, lat = location.latitude);
    }

}