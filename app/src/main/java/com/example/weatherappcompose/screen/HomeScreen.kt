package com.example.weatherappcompose.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappcompose.model.TabContentOptions
import com.example.weatherappcompose.ui.components.AccuWeatherLogo
import com.example.weatherappcompose.ui.components.CityWeatherTabContent
import com.example.weatherappcompose.ui.components.FiveDayForecastTabContent
import com.example.weatherappcompose.ui.components.OneDayForecastTabContent
import com.example.weatherappcompose.ui.components.TabView
import com.example.weatherappcompose.viewmodel.DailyForecastsViewModel

@Composable
fun HomeScreen(
    viewModel: DailyForecastsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(TabContentOptions.DAILY) }

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
                    onTabClick = { selectedTab = it },
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
