package com.example.weatherappcompose.ui.components

import com.example.weatherappcompose.util.Helpers
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.R
import com.example.weatherappcompose.ui.theme.OnPrimary
import com.example.weatherappcompose.viewmodel.AccuWeatherViewModel
import com.example.weatherappcompose.viewmodel.WeatherUiState
import java.time.OffsetDateTime

@Composable
fun OneDayForecastTabContent(viewmodel: AccuWeatherViewModel) {
    val uiState = viewmodel.weatherUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.fetchWeather(0)
    }

    when(uiState.value) {
        is WeatherUiState.Loading -> Text("Loading")
        is WeatherUiState.Error -> Text("Error")
        else -> SuccessContent(uiState.value as WeatherUiState.SuccessCurrent)
    }
}

@Composable
private fun SuccessContent(uiState: WeatherUiState.SuccessCurrent) {
    val forecast = uiState.forecast

    Box(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painterResource(R.drawable.pinicon),
                    contentDescription = "Location Icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .size(32.dp)
                )

                Text(
                    "${uiState.locationData?.EnglishName}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = OnPrimary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(2.dp))
                    .padding(8.dp)
            ) {
                // icon
                Image(
                    painterResource(Helpers.getWeatherIconResourceId(forecast?.WeatherIcon)),
                    contentDescription = forecast?.WeatherText,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .size(32.dp)
                )

                // description
                Text(
                    "${forecast?.WeatherText}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = OnPrimary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(2.dp)
                    )
            ) {
                // temperature
                Box {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            "${forecast?.Temperature?.Metric?.Value}",
                            style = MaterialTheme.typography.titleLarge,
                            color = OnPrimary
                        )

                        Text(
                            "${forecast?.Temperature?.Metric?.Unit}",
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp),
                            color = OnPrimary,
                            modifier = Modifier
                                .padding(bottom = 4.dp, start = 4.dp)
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    // Date
                    val dateTime = OffsetDateTime.parse(forecast?.LocalObservationDateTime)
                    Text(
                        "${dateTime.dayOfWeek}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = OnPrimary
                    )

                    Text(
                        "${dateTime.hour}:${dateTime.minute}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnPrimary
                    )
                }
            }
        }
    }
}