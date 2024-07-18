package com.example.zen8labs.data

import com.example.zen8labs.model.SearchResultData
import com.example.zen8labs.network.MapApiService
import com.google.android.gms.maps.model.LatLng

interface MapRepository {
    suspend fun getLocationSuggestions(query: String): List<SearchResultData>
    suspend fun getNamebyLocation(query: LatLng): String
}

class NetworkMapRepository(val retrofitService: MapApiService): MapRepository {
    override suspend fun getLocationSuggestions(query: String): List<SearchResultData> {
        return retrofitService.getLocationSuggestions(
            key = "AIzaSyBk4zYEMMR4d9WHTOuBkh2ak_rVR9sgOzI",
            input = query,
        )
    }

    override suspend fun getNamebyLocation(query: LatLng): String {
        return retrofitService.getNameByLocation(
            key = "AIzaSyBk4zYEMMR4d9WHTOuBkh2ak_rVR9sgOzI",
            location = query
        )
    }
}