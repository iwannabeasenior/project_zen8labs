package com.example.zen8labs.network

import com.example.zen8labs.model.TodayWeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    suspend fun getDataWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: Int,
        @Query("alerts") alerts: String
    ): TodayWeatherData
}