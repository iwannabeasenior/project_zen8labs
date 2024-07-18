package com.example.zen8labs.network

import com.example.zen8labs.model.SearchResultData
import com.google.android.gms.maps.model.LatLng
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {
    @GET("place/autocomplete/json")
    suspend fun getLocationSuggestions(
        @Query("key") key: String,
        @Query("input") input: String,
    ): List<SearchResultData>

    @GET("geocode/json")
    suspend fun getNameByLocation(
        @Query("latlng") location: LatLng,
        @Query("key") key: String,
    ): String
}