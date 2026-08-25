package com.example.weatherappcompose.data

import com.example.weatherappcompose.model.CurrentConditions
import com.example.weatherappcompose.model.FiveDayForecastResponse
import com.example.weatherappcompose.model.WeatherLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IAccuWeather {
    @GET("forecasts/v1/daily/5day/{locationKey}")
    suspend fun getFiveDayForecast(
        @Path("locationKey") locationKey: String?,
        @Query("apikey") apiKey: String?,
        @Query("metric") metric: Boolean
    ) : FiveDayForecastResponse?

    @GET("currentconditions/v1/{locationKey}")
    suspend fun getCurrentConditions(
        @Path("locationKey") locationKey: String?,
        @Query("apikey") apiKey: String?
    ) : List<CurrentConditions>?

    @GET("locations/v1/geoposition/search")
    suspend fun getLocationKey(
        @Query("q") q: String?,
        @Query("apikey") apiKey: String?
    ) : List<WeatherLocation>?


}