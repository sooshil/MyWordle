package com.sukajee.wordle.ui

data class GameUiState(
    val grid: MutableList<MutableList<Char>> = MutableList(size = 6) { MutableList(size = 5) { _ -> ' ' } },
    val currentGridIndex: Pair<Int, Int> = Pair(0, 0)
)
