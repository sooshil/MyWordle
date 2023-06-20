package com.sukajee.wordle.ui.screens.statsscreen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.sukajee.wordle.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: BaseRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _statUiState = MutableStateFlow(StatsUiState())
    val statUiState = _statUiState.asStateFlow()

    init {

    }

}
