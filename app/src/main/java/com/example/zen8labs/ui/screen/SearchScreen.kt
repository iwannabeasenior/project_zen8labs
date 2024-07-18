package com.example.zen8labs.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.zen8labs.ui.MapViewModel
import com.example.zen8labs.ui.WeatherViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: WeatherViewModel,
    mapViewModel: MapViewModel,
    onClickBack: () -> Unit,
) {
    val suggestions by mapViewModel.suggestions.collectAsState()
    var currentPosition = viewModel.location
    val cameraPosisionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentPosition, 8f)
    }
    var marker = rememberMarkerState(position = currentPosition)
    var searchText by remember {
        mutableStateOf("")
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
                actions = {
                  TextButton(
                      onClick = {
                          viewModel.changeLocation(currentPosition)
                          viewModel.getDataWeather()
//                          viewModel.locationToName() // billing to use ~~~
                          onClickBack()
                      }
                  ) {
                    Text("Change Location", style = MaterialTheme.typography.titleMedium)
                  }
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
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {




            GoogleMap(
                cameraPositionState = cameraPosisionState,
                onMapClick = {
                    cameraPosisionState.position =
                        CameraPosition.fromLatLngZoom(it, cameraPosisionState.position.zoom)
                    marker.position = it
                },
                modifier = Modifier.fillMaxSize(),
            ) {
                Marker(
                    state = marker,
                    title = "Marker",
                    snippet = "Marker in my location",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            tint = Color.White,
                            contentDescription = null
                        )
                    },
                    placeholder = {
                      Text("Search location...")
                    },
                    value = searchText,
                    onValueChange = {
                        searchText = it
//                        mapViewModel.updateSuggestions(searchText)
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    searchText = ""
                                    mapViewModel.clearSuggestions()
                                },
                            tint = Color.White
                        )
                    }
                )
                DropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = searchText != "",
                    onDismissRequest = {
                        //nothing
                    },

                    ) {
                    suggestions.forEach {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = {
                                Text(it.error)
                            },
                            onClick = {
                                cameraPosisionState.position =
                                    CameraPosition.fromLatLngZoom(LatLng(21.0, 105.0), cameraPosisionState.position.zoom)
                                marker.position = LatLng(21.0, 105.0)
                                searchText = it.error
                            }
                        )
                    }

                }
            }

        }
    }
}