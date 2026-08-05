package com.example.weatherappcompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.weatherappcompose.R
import com.example.weatherappcompose.model.TabContentOptions

@Composable
fun TabView(
    selectedTab: TabContentOptions,
    onTabClick: (TabContentOptions) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier
    ) {
        TabContentOptions.entries.forEach { option ->
            Tab(
                selected = selectedTab == option,
                onClick = { onTabClick(option) },
                text = {
                    val textRes = when (option) {
                        TabContentOptions.DAILY -> R.string.tab_title_one
                        TabContentOptions.FIVE_DAY -> R.string.tab_title_two
                        TabContentOptions.CITY -> R.string.tab_title_three
                    }
                    Text(text = stringResource(textRes).uppercase())
                }
            )
        }
    }
}
