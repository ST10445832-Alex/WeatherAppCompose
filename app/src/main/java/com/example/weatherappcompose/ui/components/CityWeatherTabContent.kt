package com.example.weatherappcompose.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.R
import com.example.weatherappcompose.model.TopCity
import com.example.weatherappcompose.ui.theme.OnPrimary
import com.example.weatherappcompose.util.Helpers
import com.example.weatherappcompose.viewmodel.AccuWeatherViewModel
import com.example.weatherappcompose.viewmodel.WeatherUiState
import java.time.OffsetDateTime

@Composable
fun CityWeatherTabContent(
    viewmodel: AccuWeatherViewModel,
    onCityClick: (TopCity) -> Unit = {}
) {
    val uiState by viewmodel.weatherUiState.collectAsStateWithLifecycle()

    when (uiState) {
        is WeatherUiState.Loading -> Text("Loading")
        is WeatherUiState.Error -> Text("Error")
        is WeatherUiState.SuccessCity -> SuccessContent(
            uiState as WeatherUiState.SuccessCity,
            filterCities = { filter ->
                viewmodel.filterTopCities(filter)
            },
            getWeatherByKey = { key ->
                viewmodel.getCurrentConditionsByKey(key)
            }
        )
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessContent(
    uiState: WeatherUiState.SuccessCity,
    filterCities: (String) -> Unit,
    getWeatherByKey: (String) -> Unit
) {
    val filteredCities = uiState.filteredCities?.sortedBy { it.EnglishName } ?: emptyList()
    var queryValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val selectedCityConditions = uiState.selectedCityConditions
    val selectedCity = uiState.selectedCityInfo

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = queryValue,
                    onQueryChange = {
                        queryValue = it
                        filterCities(it)
                    },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search for a city...", color = MaterialTheme.colorScheme.primary) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(4.dp))
                .padding(bottom = 16.dp),
            content = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(filteredCities) { city ->
                        ListItem(
                            headlineContent = {
                                Text(
                                    city.EnglishName ?: "Unknown",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    queryValue = city.EnglishName ?: ""
                                    getWeatherByKey(city.Key ?: "")
                                }
                                .fillMaxWidth()
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            },
        )

        if (!expanded && selectedCity != null && selectedCityConditions != null) {
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
                            "${selectedCity.EnglishName}",
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
                            painterResource(Helpers.getWeatherIconResourceId(selectedCityConditions.WeatherIcon)),
                            contentDescription = selectedCityConditions.WeatherText,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier
                                .size(32.dp)
                        )

                        // description
                        Text(
                            "${selectedCityConditions.WeatherText}",
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
                                    "${selectedCityConditions.Temperature?.Metric?.Value}",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = OnPrimary
                                )

                                Text(
                                    "${selectedCityConditions.Temperature?.Metric?.Unit}",
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
                            val dateTime = OffsetDateTime.parse(selectedCityConditions.LocalObservationDateTime)
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
    }
}
