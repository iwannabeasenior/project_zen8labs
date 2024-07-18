package com.example.zen8labs.data

import com.example.zen8labs.network.MapApiService
import com.example.zen8labs.network.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

interface AppContainer {
    val weatherRepository: WeatherRepository
    val mapRepository: MapRepository
}
class ImplAppContainer() : AppContainer {
    private val baseUrl = "https://api.weatherapi.com/v1/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()
    private val retrofitService by lazy {
        retrofit.create(WeatherApiService::class.java) // tạo 1 triển khai của giao diện WeatherApiService
    }
    override val weatherRepository: WeatherRepository by lazy { // first time access -> initialize

        NetworkWeatherRepository(retrofitService = retrofitService)
    }

    private val baseUrlMap = "https://maps.googleapis.com/maps/api/"
    private val retrofitMap: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlMap)
        .build()
    private val retrofitServiceMap by lazy {
        retrofitMap.create(MapApiService::class.java)
    }
    override val mapRepository: MapRepository by lazy {
        NetworkMapRepository(retrofitService = retrofitServiceMap)
    }
}