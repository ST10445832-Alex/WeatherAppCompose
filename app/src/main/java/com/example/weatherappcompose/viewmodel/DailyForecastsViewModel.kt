package com.example.weatherappcompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.BuildConfig
import com.example.weatherappcompose.data.RetrofitClient
import com.example.weatherappcompose.model.DailyForecasts
import kotlinx.coroutines.launch

class DailyForecastsViewModel : ViewModel() {
    private var _fiveDayForecast = MutableLiveData<List<DailyForecasts>>()

    var fiveDayForecast: LiveData<List<DailyForecasts>> = _fiveDayForecast

    init {
        getFiveDayForecast("306633")
    }

    fun getFiveDayForecast(locationKey: String) {
        viewModelScope.launch {
            try {
                val weatherData = RetrofitClient.weatherService?.
                    getFiveDayForecast(
                        locationKey = locationKey,
                        apiKey = BuildConfig.ACCUWEATHER_API_KEY,
                        metric = false
                    )
                _fiveDayForecast.value = weatherData?.DailyForecasts
            } catch (e: Exception) {
                Log.e("DailyForecastsVM", "Error fetching forecast: ${e.message}")
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