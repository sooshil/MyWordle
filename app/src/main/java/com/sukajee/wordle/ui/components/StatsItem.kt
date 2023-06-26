package com.sukajee.wordle.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsItem(
    modifier: Modifier = Modifier,
    stat: Stat,
    withPercent: Boolean = false
) {
    val value by animateIntAsState(
        targetValue = stat.value.toInt(),
        animationSpec = tween(
            durationMillis = 2000,
            easing = FastOutLinearInEasing
        )
    )
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        val text = if (withPercent) {
            buildAnnotatedString {
                append(value.toString())
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp
                    )
                ) {
                    append("%")
                }
            }
        } else buildAnnotatedString { append(value.toString()) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 48.sp
            )
            Text(
                text = stat.text,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

data class Stat(
    val text: String,
    val value: String
)
