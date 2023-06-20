package com.sukajee.wordle.ui.screens.statsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sukajee.wordle.ui.components.Stat
import com.sukajee.wordle.ui.components.StatsItem
import com.sukajee.wordle.ui.components.TopBar

@Composable
fun StatsScreen(
    navController: NavController,
    statsViewModel: StatsViewModel
) {

    val stats by statsViewModel.statUiState.collectAsState()
    StateLessStatsScreen(stats = stats)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessStatsScreen(
    modifier: Modifier = Modifier,
    stats: StatsUiState
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                title = stringResource(id = R.string.statistics).uppercase()
            )
        }
    ) { padding ->
        Column(
            modifier = modifier.padding(padding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            StatisticsRow(stats = stats, modifier = modifier)
            Spacer(modifier = Modifier.height(16.dp))
            GuessDistribution(
                stats = stats,
                modifier = modifier
            )
        }
    }
}

@Composable
fun StatisticsRow(
    stats: StatsUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val winPercent = if (stats.playStats.playedCount == 0) {
            0f.toString()
        } else {
            val percentage = stats.playStats.wonCount * 100f / stats.playStats.playedCount
            if (percentage == percentage.toInt().toFloat()) {
                percentage.toInt().toString()
            } else {
                String.format("%.1f", percentage)
            }
        }
        StatsItem(
            modifier = Modifier.weight(1f),
            stat = Stat(
                text = stringResource(id = R.string.played),
                value = stats.playStats.playedCount.toString()
            )
        )
        StatsItem(
            modifier = Modifier.weight(1f),
            stat = Stat(
                text = stringResource(id = R.string.win_percentage),
                value = winPercent
            )
        )
        StatsItem(
            modifier = Modifier.weight(1f),
            stat = Stat(
                text = stringResource(id = R.string.current_streak),
                value = stats.playStats.currentStreak.toString()
            )
        )
        StatsItem(
            modifier = Modifier.weight(1f),
            stat = Stat(
                text = stringResource(id = R.string.max_streak),
                value = stats.playStats.maxStreak.toString()
            )
        )
    }
}

@Composable
fun GuessDistribution(
    modifier: Modifier = Modifier,
    stats: StatsUiState
) {

}
