package com.sukajee.wordle.util

sealed class ErrorType {
    data class WordNotFound(val enteredWord: String) : ErrorType()
    data class WordLengthNotValid(val enteredWord: String) : ErrorType()
}
