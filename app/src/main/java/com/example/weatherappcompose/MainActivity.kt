package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.weatherappcompose.navigation.Route
import com.example.weatherappcompose.screen.HomeScreen
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import com.example.weatherappcompose.viewmodel.AccuWeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            val backStack = remember { mutableStateListOf<Route>(Route.Home) }
            WeatherAppComposeTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<Route.Home> {
                            HomeScreen(
                                viewModel = viewModel(factory = AccuWeatherViewModel.Factory(fusedLocationProviderClient))
                            )
                        }
                    }
                )
            }
        }
    }
}