package com.sukajee.wordle.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sukajee.wordle.R
import com.sukajee.wordle.ui.screens.statsscreen.StatsUiState

@Composable
fun StatisticsRow(
    stats: StatsUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val winPercent = if (stats.playStats.playedCount == 0) {
            0.toString()
        } else {
            val percentage = stats.playStats.wonCount * 100f / stats.playStats.playedCount
            percentage.toInt().toString()
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
