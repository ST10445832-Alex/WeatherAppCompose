package com.example.weatherappcompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.viewmodel.AccuWeatherViewModel
import com.example.weatherappcompose.viewmodel.WeatherUiState

@Composable
fun FiveDayForecastTabContent(viewmodel: AccuWeatherViewModel) {
    val uiState = viewmodel.weatherUiState.collectAsStateWithLifecycle()

    when(uiState.value) {
        is WeatherUiState.Loading -> Text("Loading")
        is WeatherUiState.Error -> Text("Error")
        else -> SuccessContent(uiState.value as WeatherUiState.SuccessFiveDay)
    }
}

@Composable
private fun SuccessContent(uiState: WeatherUiState.SuccessFiveDay) {
    val forecast = uiState.forecast?.DailyForecasts

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 16.dp)
    ) {
        items(forecast?.size ?: 0) { i ->
            DailyForecast(forecast?.get(i) ?: return@items)
        }
    }
}