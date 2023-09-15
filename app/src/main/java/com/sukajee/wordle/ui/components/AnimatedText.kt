package com.sukajee.wordle.ui.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import com.sukajee.wordle.ui.screens.mainscreen.Cell
import com.sukajee.wordle.util.getCharColor
import com.sukajee.wordle.util.scaledSp

@Composable
fun AnimatedText(
    cell: Cell,
    text: String
) {
    val textSize by animateIntAsState(
        targetValue = if (text != ' '.toString()) 34 else 1,
        animationSpec = tween(
            durationMillis = 400
        ), label = ""
    )

    Text(
        text = text,
        fontSize = textSize.scaledSp(),
        style = MaterialTheme.typography.headlineMedium.copy(
            platformStyle = PlatformTextStyle(includeFontPadding = false),
            fontWeight = FontWeight.Bold
        ),
        color = getCharColor(cell = cell)
    )
}
