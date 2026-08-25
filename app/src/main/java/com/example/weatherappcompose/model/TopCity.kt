package com.example.weatherappcompose.model

import com.google.gson.annotations.SerializedName


data class TopCity (

  @SerializedName("Key"                      ) var Key                      : String?      = null,
  @SerializedName("LocalizedName"            ) var LocalizedName            : String?      = null,
  @SerializedName("EnglishName"              ) var EnglishName              : String?      = null,
  @SerializedName("Country"                  ) var Country                  : Country?     = Country(),
  @SerializedName("TimeZone"                 ) var TimeZone                 : TimeZone?    = TimeZone(),
  @SerializedName("GeoPosition"              ) var GeoPosition              : GeoPosition? = GeoPosition(),
  @SerializedName("LocalObservationDateTime" ) var LocalObservationDateTime : String?      = null,
  @SerializedName("EpochTime"                ) var EpochTime                : Int?         = null,
  @SerializedName("WeatherText"              ) var WeatherText              : String?      = null,
  @SerializedName("WeatherIcon"              ) var WeatherIcon              : Int?         = null,
  @SerializedName("HasPrecipitation"         ) var HasPrecipitation         : Boolean?     = null,
  @SerializedName("PrecipitationType"        ) var PrecipitationType        : String?      = null,
  @SerializedName("IsDayTime"                ) var IsDayTime                : Boolean?     = null,
  @SerializedName("Temperature"              ) var Temperature              : Temperature? = Temperature(),
  @SerializedName("MobileLink"               ) var MobileLink               : String?      = null,
  @SerializedName("Link"                     ) var Link                     : String?      = null

)