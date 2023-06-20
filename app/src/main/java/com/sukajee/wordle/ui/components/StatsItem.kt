package com.sukajee.wordle.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsItem(
    modifier: Modifier = Modifier,
    stat: Stat
) {
    Box(
        modifier = modifier.padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stat.value, fontSize = 56.sp)
        Text(text = stat.text)
    }
}

data class Stat(
    val text: String,
    val value: String
)
