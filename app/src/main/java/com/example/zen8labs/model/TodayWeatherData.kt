package com.example.zen8labs.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodayWeatherData(
    @SerialName(value = "location") val location: Location,
    @SerialName(value = "current") val current: Current,
    @SerialName(value = "forecast") val forecast: Forecast,
)
@Serializable
data class Location(
    @SerialName(value = "name") val country: String,
)
@Serializable
data class Current(
    @SerialName(value = "temp_c") val tempC: Double,
    @SerialName(value = "humidity") val humidity: Double,
    @SerialName(value = "wind_kph") val wind: Double,
    @SerialName(value = "cloud") val cloud: Int, // Int or Double ?
    @SerialName(value = "condition") val condition: Condition,
)
@Serializable
data class Condition(
    @SerialName(value = "text") val text: String,
    @SerialName(value = "icon") val icon: String,
)
@Serializable
data class Forecast(
    @SerialName(value = "forecastday") val forecastday: List<ForecastDay>,
)
@Serializable
data class ForecastDay(
    @SerialName(value = "date") val date: String,
    @SerialName(value = "day") val day: Day,
    @SerialName(value = "hour") val hour: List<Hour>,
)
@Serializable
data class Day(
    @SerialName(value =  "maxtemp_c") val maxTempC: Double,
    @SerialName(value = "mintemp_c") val minTempC: Double,
    @SerialName(value = "daily_chance_of_rain") val chanceOfRain: Int,
    @SerialName(value = "totalprecip_mm") val totalPrecipMM: Double,
    @SerialName(value = "uv") val uv: Double,
    @SerialName(value = "condition") val conditon: Condition,
)
@Serializable
data class Hour(
    @SerialName(value = "time") val time: String,
    @SerialName(value = "condition") val condition: Condition,
    @SerialName(value = "temp_c") val tempC: Double,
)