package com.sukajee.wordle.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.util.BarType

@Composable
fun GuessDistributionBar(
    modifier: Modifier = Modifier,
    barWidth: Int,
    barValue: Int,
    barColor: Color = MaterialTheme.colorScheme.primary,
    barType: BarType = BarType.RECTANGULAR
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) { startAnimation = true }
    val animatedWidth by animateDpAsState(
        targetValue = if (startAnimation) barWidth.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 2000
        )
    )

    val shape by remember(barType) {
        mutableStateOf(
            when (barType) {
                BarType.RECTANGULAR -> RectangleShape
                BarType.FAR_END_CIRCULAR -> RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                BarType.ALL_CIRCULAR -> RoundedCornerShape(8.dp)
            }
        )
    }

    Box(
        modifier = modifier
            .width(animatedWidth)
            .height(32.dp)
            .clip(shape)
            .background(barColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = barValue.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}
