package com.example.weatherappcompose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappcompose.ui.components.AccuWeatherLogo
import com.example.weatherappcompose.ui.components.DailyForecast
import com.example.weatherappcompose.viewmodel.WeatherUiState
import com.example.weatherappcompose.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val weatherUiState = viewModel.weatherUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {},
        bottomBar = {
            AccuWeatherLogo()
        },
        content = { contentPadding ->
            when (weatherUiState.value) {
                is WeatherUiState.Loading -> {
                    Column(
                        modifier = modifier
                            .padding(contentPadding)
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Text("Loading weather data...")
                        }
                    }
                }

                is WeatherUiState.Success -> {
                    val forecasts = (weatherUiState.value as WeatherUiState.Success).dailyForecasts
                    LazyColumn(
                        modifier = modifier,
                        contentPadding = contentPadding
                    ) {
                        items(forecasts.size) { idx ->
                            DailyForecast(forecasts[idx])
                        }
                    }
                }

                is WeatherUiState.Error -> {
                    Column(
                        modifier = modifier
                            .padding(contentPadding)
                    ) {
                        Row {
                            Text((weatherUiState.value as WeatherUiState.Error).errorMessage)
                        }
                    }
                }
            }
        }
    )
}