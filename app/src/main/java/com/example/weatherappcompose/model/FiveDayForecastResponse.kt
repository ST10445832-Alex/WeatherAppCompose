package com.example.weatherappcompose.model

import com.example.weatherappcompose.model.DailyForecasts
import com.example.weatherappcompose.model.Headline
import com.google.gson.annotations.SerializedName


data class FiveDayForecastResponse (

  @SerializedName("Headline"       ) var Headline       : Headline?                 = Headline(),
  @SerializedName("DailyForecasts" ) var DailyForecasts : ArrayList<DailyForecasts> = arrayListOf()

)