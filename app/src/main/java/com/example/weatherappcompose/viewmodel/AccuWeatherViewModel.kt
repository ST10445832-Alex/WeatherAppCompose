package com.example.weatherappcompose.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.BuildConfig
import com.example.weatherappcompose.R
import com.example.weatherappcompose.data.RetrofitClient
import com.example.weatherappcompose.model.CurrentConditions
import com.example.weatherappcompose.model.FiveDayForecastResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class SuccessFiveDay(val forecast: FiveDayForecastResponse?) : WeatherUiState
    data class SuccessCurrent(
        val forecast: CurrentConditions?,
        var hasLocationPermission: Boolean
    ) : WeatherUiState
    data class SuccessCity(val forecast: CurrentConditions?) : WeatherUiState
    data class Error(val errorMessageId: Int) : WeatherUiState
}

class AccuWeatherViewModel(
    val fusedLocationProviderClient: FusedLocationProviderClient?,
) : ViewModel() {
    private var _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)

    var weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    init {
        fetchWeather(0)
    }

    fun fetchWeather(route: Int) {
        when(route) {
            0 -> getCurrentConditionsForLocation()
            1 -> getFiveDayForecast("306633")
            else -> getFiveDayForecast("306633")
        }
    }

    fun getFiveDayForecast(locationKey: String) {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading
            try {
                val weatherData = RetrofitClient.weatherService?.
                    getFiveDayForecast(
                        locationKey = locationKey,
                        apiKey = BuildConfig.ACCUWEATHER_API_KEY,
                        metric = true
                    )
                _weatherUiState.value = WeatherUiState.SuccessFiveDay(weatherData)
            } catch (e: Exception) {
                _weatherUiState.value = WeatherUiState.Error(
                    R.string.weather_data_load_error
                )
                Log.e("AccuWeatherViewModel", "Error fetching forecast: ${e.message}")
            }
        }
    }

    fun updateLocationPermission(granted: Boolean) {
        _weatherUiState.update { currentState ->
            when (currentState) {
                is WeatherUiState.SuccessCurrent -> {
                    currentState.copy(hasLocationPermission = granted)
                }
                else -> currentState
            }
        }
        if (granted) {
            fetchWeather(0)
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location {
        val location = suspendCancellableCoroutine { continuation ->
            val cancellationTokenSource = CancellationTokenSource()

            fusedLocationProviderClient?.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            )?.addOnSuccessListener { location ->
                continuation.resume(location)
            }?.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }

            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }
        return location
    }

    fun getCurrentConditionsForLocation() {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading
            try {
                val location = getCurrentLocation()
                val coordinates = "${location.latitude},${location.longitude}"
                val locationKey = RetrofitClient.weatherService?.getLocationKey(
                    q = coordinates,
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )

                val currentConditions = RetrofitClient.weatherService?.getCurrentConditions(
                    locationKey = locationKey?.firstOrNull()?.Key,
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )

                _weatherUiState.value = WeatherUiState.SuccessCurrent(
                    forecast = currentConditions?.firstOrNull(),
                    hasLocationPermission = true
                )
            } catch (exception: Exception) {
                _weatherUiState.value = WeatherUiState.Error(
                    R.string.weather_data_load_error
                )
                Log.e("AccuWeatherViewModel", "Error fetching location: ${exception.message}")
            }

        }
    }

    class Factory(
        val fusedLocationProviderClient: FusedLocationProviderClient?
    ) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>) : T {
            return AccuWeatherViewModel(
                fusedLocationProviderClient = fusedLocationProviderClient
            ) as T
        }
    }
}