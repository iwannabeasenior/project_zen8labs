package com.example.zen8labs.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.marsphotos.helper.Utils
import com.example.zen8labs.model.Day
import com.example.zen8labs.model.ForecastDay
import com.example.zen8labs.ui.WeatherUiState
import com.example.zen8labs.ui.WeatherViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(viewModel: WeatherViewModel, onClickBack: () -> Unit) {
    val data = (viewModel.uiState as WeatherUiState.Success).data
    Scaffold(
        topBar = {
            WeekScreenAppBar(onClickBack = onClickBack)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Tomorrow(data = data.forecast.forecastday.get(1).day)
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                val tomorrow = data.forecast.forecastday.get(1).day
                PropertyWeather(value = "${tomorrow.uv} ", title = "UV")
                PropertyWeather(value = "${tomorrow.chanceOfRain} %", title = "Chance of rain")
                PropertyWeather(value = "${tomorrow.totalPrecipMM.toDouble()} mm", title = "Total Precip")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                data.forecast.forecastday.forEach {
                    DayOfWeekWeather(forecastDay = it)
                }
            }
        }
    }
}

@Composable
fun Tomorrow(data: Day) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data("https:${data.conditon.icon}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(1.5f)
        )
        Column() {
            Text("Tomorrow", style = MaterialTheme.typography.titleLarge)
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(data.maxTempC.toString(), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 70.sp))
                Text("/${data.minTempC}°", style = TextStyle(color = Color.Gray.copy(alpha = 0.5f), fontSize = 30.sp))
            }
            Text(
                data.conditon.text,
                style = TextStyle(
                    color = Color.Gray.copy(alpha = 0.8f),
                    fontSize = 15.sp
                )
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekScreenAppBar(
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "7 days",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(shape = CircleShape)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        ),
                        CircleShape
                    )
                    .clickable { onClickBack() }
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(shape = CircleShape)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f)
                        ), CircleShape
                    )
            )
        }
    )

}


@Composable
fun DayOfWeekWeather(forecastDay: ForecastDay) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = Utils.dayOfWeek(forecastDay.date)?.name ?: "Unknown",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.Gray ),
        )
        Row(
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:${forecastDay.day.conditon.icon}")
                    .crossfade(true)
                    .build(),
                contentDescription = ""
            )
            Text(
//                text = forecastDay.day.conditon.text,
                text = "",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+${forecastDay.day.maxTempC}°",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "+${forecastDay.day.minTempC}°",
                color = Color.Gray
            )
        }
    }
}
