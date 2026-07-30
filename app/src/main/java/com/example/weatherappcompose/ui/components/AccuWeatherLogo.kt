package com.example.weatherappcompose.ui.components

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.R

@Composable
fun AccuWeatherLogo(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val accuWeatherUrl = "http://www.accuweather.com"

    Row( modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .navigationBarsPadding()
        .clickable { uriHandler.openUri(accuWeatherUrl) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.accuweather_logo),
            contentDescription = "AccuWeather Logo",
            modifier = Modifier
                .height(72.dp)
        )
    }
}