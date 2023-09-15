package com.sukajee.wordle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.R
import com.sukajee.wordle.ui.screens.statsscreen.StatsUiState
import com.sukajee.wordle.ui.theme.LailaFontFamily
import com.sukajee.wordle.ui.theme.VazirmatnFontFamily

@Composable
fun GuessDistribution(
    modifier: Modifier = Modifier,
    stats: StatsUiState
) {
    val maxGuessValue =
        (if (stats.guesses.isEmpty().not()) stats.guesses.maxBy { it.value }.value else 1)

    val newStats = stats.guesses.toMutableMap().also {
        it[7] = 70 /* This is added to make a box as horizontal line at the bottom of the chart. */
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(top = 24.dp),
            text = stringResource(R.string.guess_distribution),
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = LailaFontFamily,
                fontWeight = FontWeight.Bold
            ),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .padding(horizontal = 60.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if(stats.playStats.wonCount > 0) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = VazirmatnFontFamily
                ),
                textAlign = TextAlign.Justify,
                text = stringResource(R.string.guess_distribution_description)
            )
            Spacer(modifier = Modifier.height(36.dp))

            // There is at least one word has been solved. Then show the graph.
            newStats.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(if (key != 7) 50.dp else 2.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = key.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    if (key != 7) {
                        /* Vertical line */
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                    if (key != 7) {
                        BoxWithConstraints(
                            modifier = Modifier
                                .weight(10f)
                                .height((40 * LocalDensity.current.fontScale).dp),
                            contentAlignment = CenterStart
                        ) {
                            val boxWidth by remember { mutableStateOf(this.maxWidth) }
                            GuessDistributionBar(
                                barWidth = (value * boxWidth.value / maxGuessValue).toInt(),
                                barValue = value
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .offset(x = (-0.2).dp)
                                .weight(10f)
                                .height(2.dp)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = VazirmatnFontFamily
                ),
                textAlign = TextAlign.Justify,
                text = stringResource(R.string.not_enough_game_played)
            )
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}
