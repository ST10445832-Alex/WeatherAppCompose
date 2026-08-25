package com.example.weatherappcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.R
import com.example.weatherappcompose.ui.theme.OnPrimary
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
    Column(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
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
                    .padding(12.dp)
            ) {
                Image(
                    painterResource(R.drawable.pinicon),
                    contentDescription = "Location icon",
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
        }
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
}