package com.example.zen8labs.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.zen8labs.ui.WeatherViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: WeatherViewModel, onClickBack: () -> Unit) {
    var currentPosision by rememberSaveable {
        mutableStateOf(viewModel.location)
    }
    val cameraPosisionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentPosision, 8f)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onClickBack()
                            }
                    )
                },
                title = {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "Map",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            )
        },
        floatingActionButton = {
           LargeFloatingActionButton(
               onClick = {
                   viewModel.changeLocation(currentPosision)
                   viewModel.getDataWeather()
                   onClickBack()

               }
           ) {
               Text(text = "Change", style = MaterialTheme.typography.titleMedium)
            }
       }
    ) {
        GoogleMap(
            cameraPositionState = cameraPosisionState,
            onMapClick = {
                currentPosision = it
            },
            modifier = Modifier.fillMaxSize(),
        ) {
            Marker(
                state = rememberMarkerState(position = currentPosision),
                title = "Marker",
                snippet = "Marker in my location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
            )
        }
    }
}