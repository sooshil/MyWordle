package com.sukajee.wordle.ui.screens.statsscreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.wordle.model.WordleEntry
import com.sukajee.wordle.repository.BaseRepository
import com.sukajee.wordle.util.CURRENT_STREAK_COUNT
import com.sukajee.wordle.util.MAX_STREAK_COUNT
import com.sukajee.wordle.util.PLAYED_WORD_COUNT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var won = 0
    private val playedWords: MutableList<WordleEntry> = mutableListOf()
    private val guesses = mutableMapOf<Int, Int>()

    private val _statUiState = MutableStateFlow(StatsUiState())
    val statUiState = _statUiState.asStateFlow()

    init {
        viewModelScope.launch {
            won = repository.getWonCount() ?: 0
            playedWords.addAll(repository.getPlayedWordStat())
            updateUiState()
        }
    }

    private fun updateUiState() {
        val played = sharedPreferences.getInt(PLAYED_WORD_COUNT, 0)
        val currentStreak = sharedPreferences.getInt(CURRENT_STREAK_COUNT, 0)
        val maxStreak = sharedPreferences.getInt(MAX_STREAK_COUNT, 0)

        for (i in 1..6) {
            guesses[i] = playedWords.count { it.attempt == i}
        }
        _statUiState.update { currentState ->
            Log.d("TAG", "StatsViewModel: won = $won")
            currentState.copy(
                playStats = PlayStats(
                    playedCount = if (played == 0) 0 else played - 1,
                    wonCount = won,
                    currentStreak = currentStreak,
                    maxStreak = maxStreak
                ),
                guesses = guesses
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "StatsViewModel: Cleared")
    }
}
