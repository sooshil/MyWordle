package com.sukajee.wordle.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {

    private val _gameState = MutableStateFlow(GameUiState())
    val gameState = _gameState.asStateFlow()

    fun onKey(key: Char) {

    }

    fun onBackSpace() {

    }


}
