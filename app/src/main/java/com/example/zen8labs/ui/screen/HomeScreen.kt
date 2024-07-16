package com.example.zen8labs.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.zen8labs.R
import com.example.zen8labs.model.Hour
import com.example.zen8labs.model.TodayWeatherData
import com.example.zen8labs.ui.WeatherUiState
import com.example.zen8labs.ui.WeatherViewModel
import com.example.zen8labs.ui.component.MyAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WeatherViewModel,
    onClickToForecast: () -> Unit,
    onClickToMap: () -> Unit,
) {
    Scaffold(
        topBar = { MyAppBar(title = "Home", onClickToMap = onClickToMap) }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            when(viewModel.uiState) {
                is WeatherUiState.Success -> TodayScreen(
                    data = (viewModel.uiState as WeatherUiState.Success).data,
                    onClickToForecast = onClickToForecast
                )
                is WeatherUiState.Error -> ErrorScreen()
                is WeatherUiState.Loading -> LoadingScreen()
            }
        }
    }
}

@Composable
fun TodayScreen(
    data: TodayWeatherData,
    onClickToForecast: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("https:" + data.current.condition.icon)
                .crossfade(true)
                .build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),

            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .aspectRatio(1f)
        )
        Text(
            text = "${data.current.tempC}" + "°",
            fontWeight = FontWeight.Bold,
            fontSize = 80.sp,
            modifier = Modifier.wrapContentSize()
        )
        Text(
            text = data.current.condition.text,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = Color.Gray
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            PropertyWeather(value = "${data.current.humidity} %", title = "Humidity")
            PropertyWeather(value = "${data.current.wind} km/h", title = "Wind")
            PropertyWeather(value = "${data.current.cloud.toDouble()} ", title = "Cloud")
        }
        Today(onClickToForecast = onClickToForecast, data = data)
    }

}


@Composable
fun Today(
    data: TodayWeatherData,
    onClickToForecast: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text("Today", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .clickable {
                    onClickToForecast()
                }
        ) {
            Text(text = "7 days")
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,

            )
        }
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val hours = data.forecast.forecastday.get(0).hour
        items(hours) {
            hour ->
            WeatherHourToday(hour)
        }
    }
}

@Composable
fun PropertyWeather(
    value: String,
    title: String,
) {
    BoxWithConstraints() {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                Text(value)
                Text(text = title)
            }
        }
    }
}

@Composable
fun WeatherHourToday(hour: Hour) {
    Card(
        shape =  MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(5.dp),

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            Text(
                text = "${hour.tempC}" + "°",
                fontWeight = FontWeight.Bold
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data("https:" + hour.condition.icon)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )
            Text(
                text = hour.time.substring(11)
            )
        }
    }
}
@Composable
fun ErrorScreen() {
    Text(text = "Error", style = MaterialTheme.typography.titleLarge, modifier = Modifier
        .fillMaxSize()
        .background(Color.Red))
}

@Composable
fun LoadingScreen() {
    Text(text = "Loading", style = MaterialTheme.typography.titleLarge, modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue))
}