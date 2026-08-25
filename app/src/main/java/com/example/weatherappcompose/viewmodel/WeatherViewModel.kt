package com.example.weatherappcompose.viewmodel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.buildUrlForWeather
import com.example.weatherappcompose.model.FiveDayForecastResponse
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

sealed interface WeatherUiStateDeprecated {
    data object Loading : WeatherUiStateDeprecated
    data class Success(val forecast: FiveDayForecastResponse) :
        WeatherUiStateDeprecated {
        var dailyForecasts = forecast.DailyForecasts
    }
    data class Error(val errorMessage: String) :
        WeatherUiStateDeprecated
}

class WeatherViewModel : ViewModel() {
    private val _weatherUiState = MutableStateFlow<WeatherUiStateDeprecated>(WeatherUiStateDeprecated.Loading)
    val weatherUiState: StateFlow<WeatherUiStateDeprecated> = _weatherUiState.asStateFlow()

    var currentLocation by mutableStateOf<Location?>(null)
        private set

    init {
        loadWeatherData()
    }

    private fun loadWeatherData() {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiStateDeprecated.Loading

            try {
                val gson = Gson()
                val weatherJson = async { getWeatherDataJson() }.await()

                if (weatherJson.isEmpty()) {
                    throw Exception("Failed to fetch weather data")
                }

                val fiveDayForecast: Deferred<FiveDayForecastResponse> = async {gson.fromJson<FiveDayForecastResponse>(weatherJson,
                    FiveDayForecastResponse::class.java)}

                _weatherUiState.value = WeatherUiStateDeprecated.Success(fiveDayForecast.await())

                Log.i("WeatherViewModel", "Success: Fetched weather data")
            } catch (e: Exception) {
                _weatherUiState.value = WeatherUiStateDeprecated.Error("Failed to load weather data")

                Log.e("WeatherViewModel", "Failure: ${e.toString()}")
            }
        }
    }

    private suspend fun getWeatherDataJson() : String {
        try {
            return withContext(Dispatchers.IO) {
                buildUrlForWeather()?.readText() ?: ""
            }
        } catch (e: Exception) {
            Log.e("WeatherViewModel", "Failure: ${e.toString()}")
            return ""
        }
    }

    fun updateLocation(location: Location) {
        currentLocation = location
        fetchWeatherForLocation(location.latitude, location.longitude)
    }

    private fun fetchWeatherForLocation(latitude: Double, longitude: Double) {
        // retrofit call to accuweather
    }

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>) : T {
            return WeatherViewModel() as T
        }
    }
}