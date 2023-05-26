package com.sukajee.wordle.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.ui.Cell
import com.sukajee.wordle.ui.CellType
import com.sukajee.wordle.ui.GameUiState
import com.sukajee.wordle.ui.KeyState
import com.sukajee.wordle.util.ButtonType
import com.sukajee.wordle.util.DialogType
import com.sukajee.wordle.util.ErrorType
import com.sukajee.wordle.util.UiEvent
import com.sukajee.wordle.util.WordleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _keyState = MutableStateFlow(KeyState())
    val keyState = _keyState.asStateFlow()



    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentRow = 0
    private var wordList: List<String> = emptyList()
    private var allWords: List<String> = emptyList()
    private var usedWords: List<String> = emptyList()

    init {
        viewModelScope.launch {
            wordList = repository.getTopWords()
            _currentWord.value = getWord()
        }
        viewModelScope.launch {
            allWords = repository.getAllWords()
        }
    }

    fun onEvent(event: WordleEvent) {
        when (event) {
            is WordleEvent.OnKey -> onKey(event.key)
            is WordleEvent.OnSubmit -> onSubmit()
            is WordleEvent.OnHint -> onHint()
            is WordleEvent.OnBackSpace -> onBackSpace()
            is WordleEvent.OnDialogButtonClick -> onDialogButtonClick(event)
        }
    }

    private fun onKey(key: Char) {
        _gameState.update { currentState ->
            val row = currentState.currentGridIndex.first
            val column = currentState.currentGridIndex.second
            if (column > 4 || row > 5) return
            val grid = currentState.grid
            grid[row][column] = Cell(key)
            currentState.copy(
                grid = grid,
                currentGridIndex = Pair(row, column + 1)
            )
        }
    }

    private fun onBackSpace() {
        _gameState.update { currentState ->
            val row = currentState.currentGridIndex.first
            val column = currentState.currentGridIndex.second - 1
            if (column < 0 || row < 0) return
            val grid = currentState.grid
            grid[row][column] = Cell(' ')
            currentState.copy(
                grid = grid,
                currentGridIndex = Pair(row, column),
            )
        }
    }

    private fun onSubmit() {
        val enteredWord = _gameState.value.grid[currentRow].map {
            it.char
        }.joinToString().replace(", ", "")

        if (enteredWord.isDictionaryWord().not()) {
            _gameState.update { currentUiState ->
                currentUiState.copy(
                    error = ErrorType.WordNotFound(enteredWord)
                )
            }
            return
        }
        _gameState.update { currentState ->
            val grid = currentState.grid
            enteredWord.forEachIndexed { index, c ->
                if (c == _currentWord.value[index]) {
                    grid[currentRow][index] = Cell(
                        char = c,
                        cellType = CellType.CorrectCharCorrectPosition
                    )
                } else if (_currentWord.value.contains(c)) {
                    grid[currentRow][index] = Cell(
                        char = c,
                        cellType = CellType.CorrectCharWrongPosition
                    )
                } else {
                    grid[currentRow][index] = Cell(
                        char = c,
                        cellType = CellType.WrongCharWrongPosition
                    )
                }
            }
            if (enteredWord == currentWord.value || currentRow == 5) {
                currentState.copy(
                    grid = grid,
                    isGameOver = true,
                    hasWon = enteredWord == currentWord.value
                )
            } else {
                currentState.copy(
                    grid = grid,
                    currentGridIndex = Pair(++currentRow, 0)
                )
            }
        }
    }

    private fun onDialogButtonClick(event: WordleEvent.OnDialogButtonClick) {
        when (event.dialogType) {
            DialogType.GAME_OVER_DIALOG -> {
                when (event.buttonType) {
                    ButtonType.POSITIVE -> resetGameState()
                    ButtonType.NEGATIVE -> {}
                }
            }

            DialogType.ERROR_DIALOG -> {
                when (event.buttonType) {
                    ButtonType.POSITIVE -> {
                        _gameState.update { currentState ->
                            currentState.copy(
                                error = null
                            )
                        }
                    }
                    ButtonType.NEGATIVE -> {}
                }
            }
        }
    }

    private fun resetGameState() {
        currentRow = 0
        _currentWord.value = getWord()
        _gameState.update { currentState ->
            currentState.copy(
                grid = resetGrid(),
                currentGridIndex = Pair(0, 0),
                isGameOver = null,
                hasWon = null
            )
        }
    }

    private fun resetGrid() = MutableList(size = 6) {
        MutableList(size = 5) { _ ->
            Cell(char = ' ')
        }
    }

    private fun onHint() {

    }

    private fun String.isDictionaryWord() = allWords.contains(this)

    private fun getWord() = wordList.let {
        if (it.isNotEmpty()) it.random()
        else ""
    }
}
