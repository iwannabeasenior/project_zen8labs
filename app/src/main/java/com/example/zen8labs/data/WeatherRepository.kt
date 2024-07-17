package com.example.zen8labs.data

import com.example.zen8labs.model.TodayWeatherData
import com.example.zen8labs.network.WeatherApiService
// gộp chung repo(domain) + repo(data)
interface WeatherRepository {
    suspend fun getDataWeather(q: String): TodayWeatherData
}
class NetworkWeatherRepository(val retrofitService: WeatherApiService): WeatherRepository {
    override suspend fun getDataWeather(q: String): TodayWeatherData {
        return retrofitService.getDataWeather(
            key = "0e1781c4e19e4b74bb722039241107",
            q = q,
            days = 7,
            alerts = "yes"
        )
    }
}