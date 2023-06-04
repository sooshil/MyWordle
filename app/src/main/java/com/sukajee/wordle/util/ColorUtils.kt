package com.sukajee.wordle.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.sukajee.wordle.ui.Cell
import com.sukajee.wordle.ui.CellType
import com.sukajee.wordle.ui.components.KeyColorsType
import com.sukajee.wordle.ui.theme.ColorGrayDark
import com.sukajee.wordle.ui.theme.ColorGrayLight
import com.sukajee.wordle.ui.theme.ColorGreenDark
import com.sukajee.wordle.ui.theme.ColorGreenLight
import com.sukajee.wordle.ui.theme.ColorRedDark
import com.sukajee.wordle.ui.theme.ColorRedLight
import com.sukajee.wordle.ui.theme.ColorYellowDark
import com.sukajee.wordle.ui.theme.ColorYellowLight

@Composable
fun getCharColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition,
        CellType.CorrectCharWrongPosition,
        CellType.WrongCharWrongPosition -> if (isSystemInDarkTheme()) Color(0xFFEBEAEB) else Color(0xFF141414)
        CellType.EmptyCell -> MaterialTheme.colorScheme.onSurface
    }

@Composable
fun getCellColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition -> if(isSystemInDarkTheme()) ColorGreenDark else ColorGreenLight
        CellType.CorrectCharWrongPosition -> if(isSystemInDarkTheme()) ColorYellowDark else ColorYellowLight
        CellType.EmptyCell -> Color.Transparent
        CellType.WrongCharWrongPosition -> if(isSystemInDarkTheme()) ColorGrayDark else ColorGrayLight
    }

@Composable
fun getBorderColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition -> if(isSystemInDarkTheme()) ColorGreenDark else ColorGreenLight
        CellType.CorrectCharWrongPosition -> if(isSystemInDarkTheme()) ColorYellowDark else ColorYellowLight
        CellType.EmptyCell -> MaterialTheme.colorScheme.onSurface
        CellType.WrongCharWrongPosition -> if(isSystemInDarkTheme()) ColorGrayDark else ColorGrayLight
    }

@Composable
fun getKeyColor(type: KeyColorsType): Color =
    when (type) {
        KeyColorsType.GREEN -> if(isSystemInDarkTheme()) ColorGreenDark else ColorGreenLight
        KeyColorsType.RED -> if(isSystemInDarkTheme()) ColorRedDark else ColorRedLight
        KeyColorsType.ORANGE -> if(isSystemInDarkTheme()) ColorYellowDark else ColorYellowLight
    }
