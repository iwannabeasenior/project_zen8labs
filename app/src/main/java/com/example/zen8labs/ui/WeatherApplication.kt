package com.example.zen8labs.ui

import android.app.Application
import android.location.Location
import com.example.zen8labs.data.ImplWeatherContainer
import com.example.zen8labs.data.WeatherContainer
import com.google.android.gms.maps.model.LatLng

class WeatherApplication: Application(){
    lateinit var container: WeatherContainer

    override fun onCreate() {
        super.onCreate()
        container = ImplWeatherContainer()
    }
}