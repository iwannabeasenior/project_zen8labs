package com.example.zen8labs

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest;
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.zen8labs.ui.WeatherViewModel
import com.example.zen8labs.ui.screen.HomeScreen
import com.example.zen8labs.ui.screen.SearchScreen
import com.example.zen8labs.ui.theme.Zen8labsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zen8labs.ui.screen.ForecastScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

enum class WeatherScreen {
    Home,
    Search,
    Forecast,
}

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // need to process whether app have had permission or not
        // request permission for location
        var isGranted: Boolean = false

        //  check permission -> request permission -> get last location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val locationPermissionRequest  =
                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    when {
                        permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                            isGranted = true
                        }
                        permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                            isGranted = true
                        } else -> {
                        // no location access granted
                        isGranted = false
                    }
                    }
                }
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var currentLocation = LatLng(0.0, 0.0)

        fusedLocationClient.lastLocation // lastLocation là phương thức k đồng bộ
           .addOnSuccessListener { location: Location? ->
               if (location != null) {
                   currentLocation = LatLng(location.latitude, location.longitude)
               }
           }
        setContent {
            Zen8labsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MyEntry(currentLocation = currentLocation)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MyEntry(
    navController: NavHostController = rememberNavController(),
    currentLocation: LatLng,
) {
    val weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory) // start get data for today in your location, if not provided, default is HN,VN
    weatherViewModel.location = LatLng(currentLocation.latitude, currentLocation.longitude)
    NavHost(
        navController = navController,
        startDestination = WeatherScreen.Home.name,
        modifier = Modifier,
    ) {
        composable(route = WeatherScreen.Home.name) {
            HomeScreen(
                viewModel = weatherViewModel,
                onClickToForecast = { navController.navigate(WeatherScreen.Forecast.name) },
                onClickToMap = {navController.navigate(WeatherScreen.Search.name)},
            )
        }
        composable(route = WeatherScreen.Search.name) {
            SearchScreen(viewModel = weatherViewModel, onClickBack = {navController.navigateUp()})
        }
        composable(
            route = WeatherScreen.Forecast.name,
        ) {
            ForecastScreen(
                viewModel = weatherViewModel,
                onClickBack = { navController.navigateUp() }
            )
        }
    }
}

