package com.sukajee.wordle.ui.screens.mainscreen

import android.content.SharedPreferences
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.ui.GameUiState
import com.sukajee.wordle.ui.MainDispatcherRule
import com.sukajee.wordle.util.WordleEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    // Mock dependencies
    @Mock
    private lateinit var repository: BaseRepository

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    // Create an instance of MainViewModel
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        // Initialize Mockito
        MockitoAnnotations.openMocks(this)

        // Create an instance of MainViewModel
        viewModel = MainViewModel(repository, sharedPreferences)
    }

    @Test
    fun onKey() {
        var currentWord = "APPLE"
        viewModel.onEvent(WordleEvent.OnKey('A'))
        var currentGameState: GameUiState = viewModel.gameState.value

        assertTrue(currentGameState.grid[0][0].char == 'A')
    }

//    @Test
//    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset()  {
//        var currentUiState = viewModel.uiState.value
//        val correctPlayerWord = getUnscrambledWord(currentUiState.currentScrambledWord)
//
//        viewModel.updateUserGuess(correctPlayerWord)
//
//        viewModel.checkUserGuess()
//
//        currentUiState = viewModel.uiState.value
//
//        Assert.assertFalse(currentUiState.isGuessedWrong)
//        Assert.assertEquals(20, currentUiState.score)
//    }
//
//    @Test
//    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
//        val correctPlayerWord = "dummy"
//
//        viewModel.updateUserGuess(correctPlayerWord)
//
//        viewModel.checkUserGuess()
//
//        val currentUiState = viewModel.uiState.value
//
//        Assert.assertTrue(currentUiState.isGuessedWrong)
//        Assert.assertEquals(0, currentUiState.score)
//    }
//
//    @Test
//    fun gameViewModel_Initialization_FirstWordLoaded() {
//
//        val uiState = viewModel.uiState.value
//        val unscrambledWord = getUnscrambledWord(uiState.currentScrambledWord)
//
//        Assert.assertNotEquals(uiState.currentScrambledWord, unscrambledWord)
//        Assert.assertEquals(1, uiState.currentWordCount)
//        Assert.assertEquals(0, uiState.score)
//        Assert.assertFalse(uiState.isGuessedWrong)
//        Assert.assertFalse(uiState.isGameOver)
//    }
//
//    @Test
//    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
//        var expectedScore = 0
//        var currentGameUiState = viewModel.uiState.value
//        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
//
//        repeat(MAX_NO_OF_WORDS) {
//            viewModel.updateUserGuess(correctPlayerWord)
//            viewModel.checkUserGuess()
//            expectedScore += SCORE_INCREASE
//            currentGameUiState = viewModel.uiState.value
//
//            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
//            // Assert that after each correct answer, score is updated correctly.
//            Assert.assertEquals(expectedScore, currentGameUiState.score)
//        }
//        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
//        Assert.assertTrue(currentGameUiState.isGameOver)
//    }
//
//    @Test
//    fun gameViewModel_WordSkipped_ScoreUnchangedAndWordCountIncreased() {
//        var currentGameUiState = viewModel.uiState.value
//        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
//        viewModel.updateUserGuess(correctPlayerWord)
//        viewModel.checkUserGuess()
//
//        currentGameUiState = viewModel.uiState.value
//        val lastWordCount = currentGameUiState.currentWordCount
//        viewModel.skipWord()
//        currentGameUiState = viewModel.uiState.value
//        // Assert that score remains unchanged after word is skipped.
//        Assert.assertEquals(20, currentGameUiState.score)
//        // Assert that word count is increased by 1 after word is skipped.
//        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
//    }
}
