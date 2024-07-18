package com.example.zen8labs.ui

import android.app.Application
import com.example.zen8labs.data.ImplAppContainer
import com.example.zen8labs.data.AppContainer

class WeatherApplication: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ImplAppContainer()
    }
}