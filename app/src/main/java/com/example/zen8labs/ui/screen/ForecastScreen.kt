package com.example.zen8labs.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.marsphotos.helper.Utils
import com.example.zen8labs.R
import com.example.zen8labs.model.Day
import com.example.zen8labs.model.Forecast
import com.example.zen8labs.model.ForecastDay
import com.example.zen8labs.ui.WeatherUiState
import com.example.zen8labs.ui.WeatherViewModel
import com.example.zen8labs.ui.theme.OptionColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForecastScreen(viewModel: WeatherViewModel, onClickBack: () -> Unit) {
    val data = (viewModel.uiState as WeatherUiState.Success).data
    Scaffold(
        topBar = {
            WeekScreenAppBar(onClickBack = onClickBack)
        },
        containerColor = OptionColor.surface
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Tomorrow(data = data.forecast.forecastday.get(1).day, forecast = data.forecast)
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                data.forecast.forecastday.forEach {
                    DayOfWeekWeather(forecastDay = it)
                }
            }
        }
    }
}

@Composable
fun Tomorrow(data: Day, forecast: Forecast) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = OptionColor.card)
            .padding(20.dp),
    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(context = LocalContext.current)
//                .data("https:${data.conditon.icon}")
//                .crossfade(true)
//                .build(),
//            contentDescription = null,
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .size(150.dp)
//        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.tomorrow))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(170.dp)
            )
            Column() {
                Text(
                    "Tomorrow",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        data.maxTempC.toString() + "°",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        "/${data.minTempC}",
                        style = TextStyle(color = Color.White.copy(alpha = 0.5f), fontSize = 25 .sp)
                    )
                }
                Text(
                    data.conditon.text,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
            }
        }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5))
                .background(color = OptionColor.blackCard),
        ){
            val tomorrow = forecast.forecastday.get(1).day
            PropertyWeather(value = "${tomorrow.uv} ", title = "UV", icon = R.drawable.uv)
            PropertyWeather(value = "${tomorrow.chanceOfRain} %", title = "Chance of rain", icon = R.drawable.rain)
            PropertyWeather(value = "${tomorrow.totalPrecipMM} mm", title = "Total Precip", icon = R.drawable.precipitation)
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
        colors = TopAppBarDefaults.topAppBarColors(
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
            .padding(vertical = 1.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = OptionColor.card)
            .padding(vertical = 5.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Utils.dayOfWeek(forecastDay.date),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary ),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https:${forecastDay.day.conditon.icon}")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = forecastDay.day.conditon.text,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${forecastDay.day.maxTempC}°",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${forecastDay.day.minTempC}°",
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
