package com.example.weatherappcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.R
import com.example.weatherappcompose.model.DailyForecasts

@Composable
fun DailyForecast(forecast: DailyForecasts) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(forecast.Date.toString().substring(0, 10))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            val unitSymbol = forecast.Temperature?.Maximum?.Unit

            TemperatureDisplay(
                R.drawable.thermometer_min,
                forecast.Temperature?.Minimum?.Value,
                unitSymbol
            )

            TemperatureDisplay(
                R.drawable.thermometer_max,
                forecast.Temperature?.Maximum?.Value,
                unitSymbol
            )
        }
    }

    HorizontalDivider()
}


@Composable
fun TemperatureDisplay(drawableId: Int, temperature: Double?, symbol: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(drawableId),
                contentDescription = "Thermometer Cold",
                modifier = Modifier
                    .size(48.dp)
            )

            Text("$temperature $symbol")
        }
    }
}