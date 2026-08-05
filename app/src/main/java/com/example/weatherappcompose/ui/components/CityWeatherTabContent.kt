package com.example.weatherappcompose.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.viewmodel.DailyForecastsViewModel
import com.example.weatherappcompose.viewmodel.WeatherUiState

@Composable
fun CityWeatherTabContent(viewmodel: DailyForecastsViewModel) {
    val uiState = viewmodel.weatherUiState.collectAsStateWithLifecycle()

    // TODO city name input
    // TODO fetch weather data for city name
    // display weather data
    when(uiState.value) {
        is WeatherUiState.Loading -> Text("Loading")
        is WeatherUiState.Error -> Text("Error")
        else -> SuccessContent(uiState.value as WeatherUiState.Success)
    }
}

@Composable
private fun SuccessContent(uiState: WeatherUiState.Success) {

}