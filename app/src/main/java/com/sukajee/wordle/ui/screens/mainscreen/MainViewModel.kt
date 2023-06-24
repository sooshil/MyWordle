package com.sukajee.wordle.ui.screens.mainscreen

import android.content.SharedPreferences
import android.icu.text.BreakIterator.WORD_NUMBER
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.wordle.model.WordleEntry
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.ui.KeyState
import com.sukajee.wordle.util.ButtonType
import com.sukajee.wordle.util.CURRENT_STREAK_COUNT
import com.sukajee.wordle.util.DialogType
import com.sukajee.wordle.util.ErrorType
import com.sukajee.wordle.util.INTERNAL_WORD_COUNT
import com.sukajee.wordle.util.IS_FIRST_TIME_RUN
import com.sukajee.wordle.util.MAXIMUM_WORD_COUNT
import com.sukajee.wordle.util.MAX_STREAK_COUNT
import com.sukajee.wordle.util.PLAYED_WORD_COUNT
import com.sukajee.wordle.util.UiEvent
import com.sukajee.wordle.util.WordleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _gameState = MutableStateFlow(GameUiState())
    val gameState = _gameState.asStateFlow()

    private var _currentWordleEntry = MutableStateFlow(WordleEntry(word = ""))
    val currentWordleEntry = _currentWordleEntry.asStateFlow()

    private val _keyState = MutableStateFlow(KeyState())
    val keyState = _keyState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var currentRow = 0
    private var usedCharData = mutableMapOf<Char, Int>()

    private var internalWordCount = 1

    private var allWords: List<String> = emptyList()

    init {
        val isFirstTimeRun = sharedPreferences.getBoolean(IS_FIRST_TIME_RUN, true)
        if (isFirstTimeRun) {
            fetchAndSaveWords()
            sharedPreferences.edit().also {
                it.putBoolean(IS_FIRST_TIME_RUN, false)
            }.apply()
        }
        internalWordCount = sharedPreferences.getInt(INTERNAL_WORD_COUNT, 1)
        if (internalWordCount > MAXIMUM_WORD_COUNT) {
            checkIfDatabaseResetRequired()
        }
        viewModelScope.launch {
            allWords = repository.getAllWordsFromAsset()
        }
        getNewWord()
    }

    private fun fetchAndSaveWords() {
        val wordleEntries = mutableListOf<WordleEntry>()
        viewModelScope.launch {
            val words = repository.getTopWordsFromAsset()
            words.forEach { word ->
                wordleEntries.add(
                    WordleEntry(word = word)
                )
            }
            repository.insertAllWords(wordleEntries)
        }
    }

    private fun onSubmit() {
        val enteredWord = _gameState.value.grid[currentRow].map {
            it.char
        }.joinToString().replace(", ", "")
        if (enteredWord.trim().isEmpty()) return
        if (enteredWord.trim().length < 5) {
            _gameState.update { currentUiState ->
                currentUiState.copy(
                    error = ErrorType.WordLengthNotValid(enteredWord)
                )
            }
            return
        }
        if (enteredWord.isDictionaryWord().not()) {
            _gameState.update { currentUiState ->
                currentUiState.copy(
                    error = ErrorType.WordNotFound(enteredWord)
                )
            }
            return
        }
        updateGameState(enteredWord)
    }

    private fun updateGameState(enteredWord: String) {
        internalWordCount = sharedPreferences.getInt(INTERNAL_WORD_COUNT, 1)
        var currentStreak = sharedPreferences.getInt(CURRENT_STREAK_COUNT, 0)
        var maxStreak = sharedPreferences.getInt(MAX_STREAK_COUNT, 0)
        _gameState.update { currentState ->
            val grid = currentState.grid
            enteredWord.forEachIndexed { index, c ->
                if (c == _currentWordleEntry.value.word[index]) {
                    handleCorrectCharCorrectPlace(grid, index, c)
                } else if (_currentWordleEntry.value.word.contains(c)) {
                    if (enteredWord.count { it == c } == 1) {
                        grid[currentRow][index] = Cell(
                            char = c,
                            cellType = CellType.CorrectCharWrongPosition
                        )
                    } else {
                        val charPositionsInCurrentWord =
                            getCharIndices(_currentWordleEntry.value.word, c)
                        val charPositionsInEnteredWord =
                            getCharIndices(enteredWord, c)
                        val diff =
                            charPositionsInCurrentWord.minus(charPositionsInEnteredWord.toSet())

                        if ((usedCharData[c] ?: 0) >= diff.size) {
                            grid[currentRow][index] = Cell(
                                char = c,
                                cellType = CellType.WrongCharWrongPosition
                            )
                        } else {
                            when (diff.size) {
                                0 -> {
                                    grid[currentRow][index] = Cell(
                                        char = c,
                                        cellType = CellType.WrongCharWrongPosition
                                    )
                                }

                                else -> {
                                    grid[currentRow][index] =
                                        Cell(
                                            char = c,
                                            cellType = CellType.CorrectCharWrongPosition
                                        )
                                    usedCharData[c] = (usedCharData[c] ?: 0) + 1
                                }
                            }
                        }
                    }
                    _keyState.update { currentState ->
                        currentState.redKeyList.remove(c)
                        if (currentState.greenKeyList.contains(c)
                                .not()
                        ) currentState.orangeKeyList.add(c)
                        currentState.copy(
                            orangeKeyList = currentState.orangeKeyList
                        )
                    }
                } else {
                    grid[currentRow][index] = Cell(
                        char = c,
                        cellType = CellType.WrongCharWrongPosition
                    )
                    _keyState.update { currentState ->
                        if (currentState.greenKeyList.contains(c).not()
                            && currentState.orangeKeyList.contains(c).not()
                        ) currentState.redKeyList.add(c)
                        currentState.copy(
                            redKeyList = currentState.redKeyList
                        )
                    }
                }
            }
            usedCharData = mutableMapOf()
            val hasWon = enteredWord == _currentWordleEntry.value.word
            if (hasWon) {
                viewModelScope.launch {
                    repository.insertWord(
                        WordleEntry(
                            entryId = _currentWordleEntry.value.entryId,
                            word = _currentWordleEntry.value.word,
                            isSolved = true,
                            attempt = currentRow + 1
                        )
                    )
                }

                sharedPreferences.edit().also {
                    it.putInt(INTERNAL_WORD_COUNT, ++internalWordCount)
                    it.putInt(CURRENT_STREAK_COUNT, ++currentStreak)
                    if (maxStreak < currentStreak) {
                        it.putInt(MAX_STREAK_COUNT, ++maxStreak)
                    }
                }.apply()
            }
            if (hasWon || currentRow == 5) {
                var playedWords = sharedPreferences.getInt(PLAYED_WORD_COUNT, 1)
                sharedPreferences.edit().also {
                    if(hasWon.not()) it.putInt(CURRENT_STREAK_COUNT, 0)
                    it.putInt(PLAYED_WORD_COUNT, ++playedWords)
                }.apply()

                currentState.copy(
                    grid = grid,
                    isGameOver = true,
                    hasWon = hasWon
                )
            } else {
                currentState.copy(
                    grid = grid,
                    currentGridIndex = Pair(++currentRow, 0)
                )
            }
        }
    }

    private fun handleCorrectCharCorrectPlace(
        grid: MutableList<MutableList<Cell>>,
        index: Int,
        c: Char
    ) {
        grid[currentRow][index] = Cell(
            char = c,
            cellType = CellType.CorrectCharCorrectPosition
        )
        _keyState.update { currentState ->
            currentState.orangeKeyList.remove(c)
            currentState.redKeyList.remove(c)
            currentState.greenKeyList.add(c)
            currentState.copy(
                greenKeyList = currentState.greenKeyList
            )
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

    private fun onDialogButtonClick(event: WordleEvent.OnDialogButtonClick) {
        when (event.dialogType) {
            DialogType.GAME_OVER_DIALOG -> {
                when (event.buttonType) {
                    ButtonType.POSITIVE -> resetGameState(event.hasWon)
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

    private fun resetGameState(hasWon: Boolean) {
        currentRow = 0
        if(hasWon.not()) getNewWord()
        /* getNewWord() This is not required as once we update the correct word submission
        to the database, it will emmit the new word automatically. So, this is redundant. */
        _gameState.update { currentState ->
            currentState.copy(
                grid = resetGrid(),
                currentGridIndex = Pair(0, 0),
                isGameOver = null,
                hasWon = null,
                currentWordNumber = sharedPreferences.getInt(PLAYED_WORD_COUNT, 1)
            )
        }
        _keyState.update { currentState ->
            currentState.copy(
                redKeyList = mutableSetOf(),
                orangeKeyList = mutableSetOf(),
                greenKeyList = mutableSetOf()
            )
        }
    }

    private fun checkIfDatabaseResetRequired() {
        viewModelScope.launch {
            repository.deleteAllWords()
            fetchAndSaveWords()
        }
        sharedPreferences.edit().also { it.putInt(INTERNAL_WORD_COUNT, 1) }.apply()
    }

    private fun resetGrid() = MutableList(size = 6) {
        MutableList(size = 5) { _ ->
            Cell(char = ' ')
        }
    }

    private fun onHint() {

    }

    private fun String.isDictionaryWord() = allWords.contains(this)

    fun onEvent(event: WordleEvent) {
        when (event) {
            is WordleEvent.OnKey -> onKey(event.key)
            is WordleEvent.OnSubmit -> onSubmit()
            is WordleEvent.OnHint -> onHint()
            is WordleEvent.OnBackSpace -> onBackSpace()
            is WordleEvent.OnDialogButtonClick -> onDialogButtonClick(event)
        }
    }

    private fun getNewWord() = viewModelScope.launch {
        _gameState.update {
            it.copy(
                currentWordNumber = sharedPreferences.getInt(PLAYED_WORD_COUNT, 1)
            )
        }
        repository.getWord()
            .filterNotNull()
            .collectLatest { wordleEntry ->
                _currentWordleEntry.value = wordleEntry
            }
    }

    private fun getCharIndices(word: String, char: Char): List<Int> {
        return word.mapIndexedNotNull { index, c -> if (c == char) index else null }
    }
}
