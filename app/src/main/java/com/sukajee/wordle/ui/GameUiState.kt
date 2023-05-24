package com.sukajee.wordle.ui

import com.sukajee.wordle.util.ErrorType

data class GameUiState(
    val grid: MutableList<MutableList<Cell>> = MutableList(size = 6) {
        MutableList(size = 5) { _ ->
            Cell(char = ' ')
        }
    },
    val currentGridIndex: Pair<Int, Int> = Pair(0, 0),
    val error: ErrorType? = null,
    val isGameOver: Boolean? = null,
    val isWon: Boolean? = null
)

data class Cell(
    val char: Char,
    val cellType: CellType = CellType.EmptyCell
)

sealed class CellType {
    object CorrectCharCorrectPosition : CellType()
    object CorrectCharWrongPosition : CellType()
    object WrongCharWrongPosition : CellType()
    object EmptyCell : CellType()
}
