package com.example.zen8labs.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.zen8labs.data.MapRepository
import com.example.zen8labs.model.SearchResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(val repository: MapRepository) : ViewModel() {
    private val _suggestions = MutableStateFlow<List<SearchResultData>>(emptyList())
    val suggestions: StateFlow<List<SearchResultData>> = _suggestions

    fun updateSuggestions(query: String) {
        viewModelScope.launch {
            val response = getLocationSuggestions(query)
            _suggestions.value = response
        }
    }

    suspend fun getLocationSuggestions(query: String): List<SearchResultData> {
        return repository.getLocationSuggestions(query)
    }

    fun clearSuggestions() {
        _suggestions.value = emptyList()
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WeatherApplication)
                val repository = application.container.mapRepository
                MapViewModel(repository)
            }
        }
    }
}