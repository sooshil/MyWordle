package com.sukajee.wordle.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.sukajee.wordle.ui.Cell
import com.sukajee.wordle.ui.CellType

@Composable
fun getCharColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition,
        CellType.CorrectCharWrongPosition,
        CellType.WrongCharWrongPosition -> if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
        CellType.EmptyCell -> MaterialTheme.colorScheme.onSurface
    }

@Composable
fun getCellColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition -> Green
        CellType.CorrectCharWrongPosition -> Yellow
        CellType.EmptyCell -> Color.Transparent
        CellType.WrongCharWrongPosition -> LightGray
    }

@Composable
fun getBorderColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition -> Green
        CellType.CorrectCharWrongPosition -> Yellow
        CellType.EmptyCell -> MaterialTheme.colorScheme.onSurface
        CellType.WrongCharWrongPosition -> LightGray
    }
