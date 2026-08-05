package com.example.weatherappcompose.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.BuildConfig
import com.example.weatherappcompose.R
import com.example.weatherappcompose.data.RetrofitClient
import com.example.weatherappcompose.model.DailyForecasts
import com.example.weatherappcompose.model.FiveDayForecastResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val forecast: FiveDayForecastResponse?) : WeatherUiState
    data class Error(val errorMessageId: Int) : WeatherUiState
}

class DailyForecastsViewModel : ViewModel() {
    private var _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)

    var weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    init {
        getFiveDayForecast("306633")
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
                _weatherUiState.value = WeatherUiState.Success(weatherData)
            } catch (e: Exception) {
                _weatherUiState.value = WeatherUiState.Error(
                    R.string.weather_data_load_error
                )
                Log.e("DailyForecastsViewModel", "Error fetching forecast: ${e.message}")
            }
        }
    }

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>) : T {
            return DailyForecastsViewModel() as T
        }
    }
}