package com.example.weatherappcompose.data

import com.example.weatherappcompose.model.FiveDayForecastResponse
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
}