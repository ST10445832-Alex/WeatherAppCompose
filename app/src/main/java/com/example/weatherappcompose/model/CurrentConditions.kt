package com.example.weatherappcompose.model

import com.google.gson.annotations.SerializedName


data class CurrentConditions(

  @SerializedName("LocalObservationDateTime" ) var LocalObservationDateTime : String?      = null,
  @SerializedName("EpochTime"                ) var EpochTime                : Int?         = null,
  @SerializedName("WeatherText"              ) var WeatherText              : String?      = null,
  @SerializedName("WeatherIcon"              ) var WeatherIcon              : Int?         = null,
  @SerializedName("HasPrecipitation"         ) var HasPrecipitation         : Boolean?     = null,
  @SerializedName("PrecipitationType"        ) var PrecipitationType        : String?      = null,
  @SerializedName("IsDayTime"                ) var IsDayTime                : Boolean?     = null,
  @SerializedName("Temperature"              ) var Temperature              : TemperatureCurrent? = TemperatureCurrent(),
  @SerializedName("MobileLink"               ) var MobileLink               : String?      = null,
  @SerializedName("Link"                     ) var Link                     : String?      = null

)