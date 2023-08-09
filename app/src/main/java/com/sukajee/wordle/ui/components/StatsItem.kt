package com.sukajee.wordle.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.util.ColorFor
import com.sukajee.wordle.util.getColor
import com.sukajee.wordle.util.scaledSp

@Composable
fun StatsItem(
    modifier: Modifier = Modifier,
    stat: Stat
) {
    val value by animateIntAsState(
        targetValue = stat.value.toInt(),
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutLinearInEasing
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.aspectRatio(1f),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(getColor(forWhat = ColorFor.STATS_ITEM_BACKGROUND)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    fontSize = 40.scaledSp(),
                    color = getColor(forWhat = ColorFor.STATS_ITEM_TEXT)
                )
            }
        }
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stat.text,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            textAlign = TextAlign.Center
        )
    }
}

data class Stat(
    val text: String,
    val value: String
)
