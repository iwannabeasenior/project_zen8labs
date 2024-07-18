package com.example.zen8labs.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultData(
//    @SerialName(value = "name") val name: String,
//    @SerialName(value = "id") val id: String,
//    @SerialName(value = "location") val location: LatLng,
    @SerialName(value = "error_message") val error: String
)