package com.sukajee.wordle.ui

data class GameUiState(
    val grid: List<List<Char>> = List(size = 6) { List(size = 5) { _ -> ' ' } },
    val currentGridIndex: Pair<Int, Int> = Pair(0, 0)
)
