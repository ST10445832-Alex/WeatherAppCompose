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
import com.example.weatherappcompose.model.TopCity
import com.example.weatherappcompose.model.WeatherLocation
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
    data class SuccessFiveDay(
        val forecast: FiveDayForecastResponse?,
        val locationData: WeatherLocation?
    ) : WeatherUiState
    data class SuccessCurrent(
        val forecast: CurrentConditions?,
        val locationData: WeatherLocation?,
        var hasLocationPermission: Boolean
    ) : WeatherUiState
    data class SuccessCity(
        val cities: List<TopCity>?,
        val filteredCities: List<TopCity>?,
        val selectedCityInfo: TopCity? = null,
        val selectedCityConditions: CurrentConditions? = null
    ) : WeatherUiState
    data class Error(val errorMessageId: Int) : WeatherUiState
}

class AccuWeatherViewModel(
    val fusedLocationProviderClient: FusedLocationProviderClient?,
) : ViewModel() {
    private var _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)

    var weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    fun fetchWeather(route: Int) {
        when(route) {
            0 -> getCurrentConditionsForLocation()
            1 -> getFiveDayForecast()
            2 -> getTopCitiesConditions()
            else -> getFiveDayForecast()
        }
    }

    fun getFiveDayForecast() {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading
            try {
                val geolocation = getCurrentLocation()
                val coordinates = "${geolocation.latitude},${geolocation.longitude}"
                val weatherLocation = RetrofitClient.weatherService?.getLocationKey(
                    q = coordinates,
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )
                val weatherData = RetrofitClient.weatherService?.
                    getFiveDayForecast(
                        locationKey = weatherLocation?.firstOrNull()?.Key,
                        apiKey = BuildConfig.ACCUWEATHER_API_KEY,
                        metric = true
                    )
                _weatherUiState.value = WeatherUiState.SuccessFiveDay(
                    weatherData,
                    locationData = weatherLocation?.firstOrNull()
                )
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
                val geolocation = getCurrentLocation()
                val coordinates = "${geolocation.latitude},${geolocation.longitude}"
                val weatherLocation = RetrofitClient.weatherService?.getLocationKey(
                    q = coordinates,
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )

                val currentConditions = RetrofitClient.weatherService?.getCurrentConditions(
                    locationKey = weatherLocation?.firstOrNull()?.Key,
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )

                _weatherUiState.value = WeatherUiState.SuccessCurrent(
                    forecast = currentConditions?.firstOrNull(),
                    hasLocationPermission = true,
                    locationData = weatherLocation?.firstOrNull()
                )
            } catch (exception: Exception) {
                _weatherUiState.value = WeatherUiState.Error(
                    R.string.weather_data_load_error
                )
                Log.e("AccuWeatherViewModel", "Error fetching location: ${exception.message}")
            }
        }
    }

    fun getTopCitiesConditions() {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading

            try {
                val topCityConditions = RetrofitClient.weatherService?.getTopCitiesConditions(
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )

                _weatherUiState.value = WeatherUiState.SuccessCity(
                    cities = topCityConditions,
                    filteredCities = topCityConditions
                )
            } catch (exception: Exception) {
                _weatherUiState.value = WeatherUiState.Error(
                    R.string.weather_data_load_error
                )
                Log.e("AccuWeatherViewModel", "Error fetching location: ${exception.message}")
            }
        }
    }

    fun getCurrentConditionsByKey(key: String) {
        viewModelScope.launch {
            try {
                val currentConditions = RetrofitClient.weatherService?.getCurrentConditions(
                    key,
                    apiKey = BuildConfig.ACCUWEATHER_API_KEY
                )?.firstOrNull()

                _weatherUiState.update { currentState ->
                    if (currentState is WeatherUiState.SuccessCity) {
                        val cityInfo = currentState.cities?.find { it.Key == key }
                        currentState.copy(
                            selectedCityInfo = cityInfo,
                            selectedCityConditions = currentConditions
                        )
                    } else {
                        currentState
                    }
                }
            } catch (exception: Exception) {
                Log.e("AccuWeatherViewModel", "Error fetching conditions by key: ${exception.message}")
            }
        }
    }

    fun filterTopCities(filter: String) {
        _weatherUiState.update { currentState ->
            when (currentState) {
                is WeatherUiState.SuccessCity -> {
                    currentState.copy(
                        filteredCities = if (filter.isBlank()) {
                            currentState.cities
                        } else {
                            currentState.cities?.filter { city ->
                                city.EnglishName?.startsWith(filter, ignoreCase = true) == true
                            }
                        }
                    )
                }
                else -> currentState
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
