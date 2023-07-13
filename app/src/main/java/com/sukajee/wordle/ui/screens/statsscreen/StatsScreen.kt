package com.sukajee.wordle.ui.screens.statsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sukajee.wordle.R
import com.sukajee.wordle.navigation.Screen
import com.sukajee.wordle.ui.components.GuessDistribution
import com.sukajee.wordle.ui.components.StatisticsRow
import com.sukajee.wordle.ui.components.TopBar

@Composable
fun StatsScreen(
    navController: NavController,
    statsViewModel: StatsViewModel
) {
    val stats by statsViewModel.statUiState.collectAsState()
    StateLessStatsScreen(
        stats = stats,
        onClick = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessStatsScreen(
    modifier: Modifier = Modifier,
    stats: StatsUiState,
    onClick: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.statistics).uppercase(),
                screenName = Screen.StatsScreen,
                onClick = onClick
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            StatisticsRow(stats = stats)
            Spacer(modifier = Modifier.height(16.dp))
            GuessDistribution(
                stats = stats,
                modifier = modifier
            )
        }
    }
}
