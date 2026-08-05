package com.example.weatherappcompose.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.R
import com.example.weatherappcompose.ui.theme.OnPrimary
import com.example.weatherappcompose.ui.theme.PrimaryFixedDim
import com.example.weatherappcompose.viewmodel.DailyForecastsViewModel
import com.example.weatherappcompose.viewmodel.WeatherUiState

@Composable
fun OneDayForecastTabContent(viewmodel: DailyForecastsViewModel) {
    val uiState = viewmodel.weatherUiState.collectAsStateWithLifecycle()

    when(uiState.value) {
        is WeatherUiState.Loading -> Text("Loading")
        is WeatherUiState.Error -> Text("Error")
        else -> SuccessContent(uiState.value as WeatherUiState.Success)
    }
}

@Composable
private fun SuccessContent(uiState: WeatherUiState.Success) {
    val forecast = uiState.forecast?.DailyForecasts[0]
    Box(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
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
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(2.dp))
                    .padding(8.dp)
            ) {
                // icon
                Image(
                    painterResource(R.drawable.cloudy),
                    contentDescription = forecast?.Day?.IconPhrase,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .size(32.dp)
                )

                // description
                Text(
                    "${forecast?.Day?.IconPhrase}",
                    style = MaterialTheme.typography.headlineLarge,
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
                            "${forecast?.Temperature?.Maximum?.Value}",
                            style = MaterialTheme.typography.titleLarge,
                            color = OnPrimary
                        )

                        Text(
                            "${forecast?.Temperature?.Maximum?.Unit}",
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
                    // low - high
                    Text(
                        "HI ${forecast?.Temperature?.Maximum?.Value}°",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnPrimary
                    )

                    Text(
                        "Lo ${forecast?.Temperature?.Minimum?.Value}°",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnPrimary
                    )
                }
            }
        }
    }
}