package com.sukajee.wordle.ui

import androidx.compose.ui.graphics.Color

data class KeyState(
    val char: Char = ' ',
    val keyColor: Color = Color.DarkGray
)
