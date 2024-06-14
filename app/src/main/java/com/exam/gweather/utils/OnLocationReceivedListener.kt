package com.exam.gweather.utils

import android.location.Location

interface OnLocationReceivedListener {
    fun onLocationReceived(location: Location?)
}