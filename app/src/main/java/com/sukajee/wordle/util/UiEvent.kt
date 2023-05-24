package com.sukajee.wordle.util

/* Class that holds the events going from viewmodel to Ui. */

sealed class UiEvent {
    data class ShowErrorAlert(val errorType: ErrorType) : UiEvent()
}
