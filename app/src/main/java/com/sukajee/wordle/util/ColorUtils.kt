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
        CellType.CorrectCharCorrectPosition -> if(isSystemInDarkTheme()) Color(0xFF208602) else Color(0xFF2CB803)
        CellType.CorrectCharWrongPosition -> if(isSystemInDarkTheme()) Color(0xFFA37502) else Color(0xFFB68610)
        CellType.EmptyCell -> Color.Transparent
        CellType.WrongCharWrongPosition -> if(isSystemInDarkTheme()) Color(0xFF616161) else Color(0xFF808080)
    }

@Composable
fun getBorderColor(cell: Cell): Color =
    when (cell.cellType) {
        CellType.CorrectCharCorrectPosition -> if(isSystemInDarkTheme()) Color(0xFF208602) else Color(0xFF2CB803)
        CellType.CorrectCharWrongPosition -> if(isSystemInDarkTheme()) Color(0xFFA37502) else Color(0xFFB68610)
        CellType.EmptyCell -> MaterialTheme.colorScheme.onSurface
        CellType.WrongCharWrongPosition -> if(isSystemInDarkTheme()) Color(0xFF616161) else Color(0xFF808080)
    }

@Composable
fun getKeyColor(type: KeyColorsType): Color =
    when (type) {
        KeyColorsType.GREEN -> if(isSystemInDarkTheme()) Color(0xFF208602) else Color(0xFF2CB803)
        KeyColorsType.RED -> if(isSystemInDarkTheme()) Color(0xFF751212) else Color(0xFFA72121)
        KeyColorsType.ORANGE -> if(isSystemInDarkTheme()) Color(0xFFA37502) else Color(0xFFB68610)
    }
