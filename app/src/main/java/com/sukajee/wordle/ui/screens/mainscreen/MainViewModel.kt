package com.sukajee.wordle.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.ui.GameUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: BaseRepository
) : ViewModel() {

    private val _gameState = MutableStateFlow(GameUiState())
    val gameState = _gameState.asStateFlow()

    private var _currentWord = MutableStateFlow("")
    val currentWord = _currentWord.asStateFlow()


    private var wordList: List<String> = emptyList()
    private var allWords: List<String> = emptyList()
    private var usedWords: List<String> = emptyList()

    var isNextRowOpen = false

    init {
        viewModelScope.launch {
            wordList = repository.getTopWords()
            _currentWord.value = getWord()
        }
        viewModelScope.launch {
            allWords = repository.getAllWords()
        }
    }

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
        _gameState.update { currentState ->

            val row = if(currentState.currentGridIndex.first >= 5) 5 else currentState.currentGridIndex.first + 1
            currentState.copy(
                currentGridIndex = Pair(row, 0)
            )
        }
    }

    fun onHint() {

    }

    private fun getWord() = wordList.let {
        if(it.isNotEmpty()) it.random()
        else ""
    }
}
