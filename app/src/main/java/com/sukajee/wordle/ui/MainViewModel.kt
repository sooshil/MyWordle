package com.sukajee.wordle.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameUiState())
    val gameState = _gameState.asStateFlow()

    var isNextRowOpen = false

    fun onKey(key: Char) {
        _gameState.update { currentState ->
            val row = currentState.currentGridIndex.first
            val column = currentState.currentGridIndex.second
            if (column > 4 || row > 5) return
            val grid = currentState.grid
            grid[row][column] = key
            currentState.copy(
                grid = grid,
                currentGridIndex = Pair(row, column + 1)
            )
        }
    }

    fun onBackSpace() {
        _gameState.update { currentState ->
            val row = currentState.currentGridIndex.first
            val column = currentState.currentGridIndex.second - 1
            if (column < 0 || row < 0) return
            val grid = currentState.grid
            grid[row][column] = ' '
            currentState.copy(
                grid = grid,
                currentGridIndex = Pair(row, column)
            )
        }
    }

    fun onSubmit() {

    }

    fun onHint() {

    }
}
