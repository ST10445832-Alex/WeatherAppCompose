package com.example.weatherappcompose.ui.components

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.R
import com.example.weatherappcompose.model.DailyForecasts

@Composable
fun DailyForecast(forecast: DailyForecasts) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(4.dp)
            .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 8.dp)
        ) {
            Text(
                forecast.Date.toString().substring(0, 10),
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val unitSymbol = forecast.Temperature?.Maximum?.Unit

            TemperatureDisplay(
                "Lo",
                forecast.Temperature?.Minimum?.Value,
                unitSymbol
            )

            TemperatureDisplay(
                "HI",
                forecast.Temperature?.Maximum?.Value,
                unitSymbol
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
    )
}


@Composable
fun TemperatureDisplay(label: String, temperature: Double?, symbol: String?) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "$label ",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Box {
            Text(
                "$temperature",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = "°",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}