package com.example.weatherappcompose.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherappcompose.model.TabContentOptions
import com.example.weatherappcompose.ui.components.AccuWeatherLogo
import com.example.weatherappcompose.ui.components.CityWeatherTabContent
import com.example.weatherappcompose.ui.components.FiveDayForecastTabContent
import com.example.weatherappcompose.ui.components.OneDayForecastTabContent
import com.example.weatherappcompose.ui.components.TabView
import com.example.weatherappcompose.viewmodel.AccuWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun HomeScreen(
    viewModel: AccuWeatherViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(TabContentOptions.DAILY) }
    var context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                      permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        viewModel.updateLocationPermission(granted)
    }

    LaunchedEffect(Unit) {
        val fineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val coarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (
            fineLocation == PackageManager.PERMISSION_GRANTED ||
            coarseLocation == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.updateLocationPermission(true)
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
            ) {
                AccuWeatherLogo()
                TabView(
                    selectedTab = selectedTab,
                    onTabClick = {
                        selectedTab = it
                        viewModel.fetchWeather(selectedTab.ordinal)
                    },
                )
            }
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            when (selectedTab) {
                TabContentOptions.DAILY -> OneDayForecastTabContent(viewmodel = viewModel)
                TabContentOptions.FIVE_DAY -> FiveDayForecastTabContent(viewmodel = viewModel)
                TabContentOptions.CITY -> CityWeatherTabContent(viewmodel = viewModel)
            }
        }
    }
}
