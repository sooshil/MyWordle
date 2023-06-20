package com.sukajee.wordle.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sukajee.wordle.ui.screens.mainscreen.Cell
import com.sukajee.wordle.util.getCharColor

@Composable
fun AnimatedText(
    cell: Cell,
    text: String
) {
    val textSize by animateFloatAsState(
        targetValue = if (text != ' '.toString()) 34f else 5f,
        animationSpec = tween(
            durationMillis = 300
        )
    )

    Text(
        text = text,
        fontSize = textSize.sp,
        style = TextStyle(
            platformStyle = PlatformTextStyle(includeFontPadding = false),
            fontWeight = FontWeight.SemiBold
        ),
        color = getCharColor(cell = cell)
    )
}
