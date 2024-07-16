package com.example.zen8labs.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.zen8labs.data.WeatherRepository
import com.example.zen8labs.model.TodayWeatherData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// State for Api load today
sealed interface WeatherUiState {
    data class Success(val data: TodayWeatherData): WeatherUiState
    object Error: WeatherUiState
    object Loading: WeatherUiState
}
class WeatherViewModel(val weatherRepository: WeatherRepository): ViewModel() {

    var uiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)

    // Default: Viet Nam
    var location by mutableStateOf(LatLng(62.0, 105.1))

    fun changeLocation(newLocation: LatLng) {
        location = newLocation
    }
    init {
        getDataWeather()
    }
    fun getDataWeather() {
        viewModelScope.launch {
            uiState = try {
                WeatherUiState.Success(weatherRepository.getDataWeather(q = "${location.latitude},${location.longitude}"))
            } catch (e: IOException) {
                Log.e("loi roi", e.message.toString())
                WeatherUiState.Error
            } catch (e: HttpException) {
                Log.e("loi roi", e.message.toString())
                WeatherUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =(this[APPLICATION_KEY] as WeatherApplication)
                // Get weatherRepository from container
                val weatherRepository = application.container.weatherRepository
                WeatherViewModel(weatherRepository = weatherRepository)
            }
        }
    }
}