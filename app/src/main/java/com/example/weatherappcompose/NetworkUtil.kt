package com.example.weatherappcompose

import android.net.Uri
import android.util.Log
import java.net.URL

private val BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605"
private val PARAM_METRIC = "metric"
private val METRIC_VALUE = "true"
private val PARAM_API_KEY = "apikey"
private val LOGGING_TAG = "URLWECREATED"

fun buildUrlForWeather(): URL? {
    val buildUri: Uri = Uri.parse(BASE_URL).buildUpon()
        .appendQueryParameter(
            PARAM_API_KEY,
            BuildConfig.ACCUWEATHER_API_KEY
        )
        .appendQueryParameter(
            PARAM_METRIC,
            METRIC_VALUE
        )
        .build()
    var url: URL? = null
    try {
        url = URL(buildUri.toString())
    } catch(e: Exception) {
        e.printStackTrace()
    }
    Log.i(LOGGING_TAG, "buildUrlForWeather: $url")
    return url
}