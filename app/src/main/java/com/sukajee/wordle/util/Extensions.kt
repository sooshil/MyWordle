package com.sukajee.wordle.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

fun String.padWithZeros(desiredLength: Int = 3): String {
    val zerosToAdd = desiredLength - length
    return "0".repeat(zerosToAdd.coerceAtLeast(0)) + this
}

@Composable
fun Int.scaledSp(): TextUnit {
    val value: Int = this
    return with(LocalDensity.current) {
        val fontScale = this.fontScale
        val textSize = value / fontScale
        textSize.sp
    }
}
