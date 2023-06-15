package com.sukajee.wordle.util

/* Class that holds the events from ui to viewmodel. */

sealed class WordleEvent {
    object OnSubmit : WordleEvent()
    object OnHint : WordleEvent()
    data class OnKey(val key: Char) : WordleEvent()
    object OnBackSpace : WordleEvent()
    data class OnDialogButtonClick(
        val buttonType: ButtonType,
        val dialogType: DialogType
    ) : WordleEvent()
}

enum class DialogType {
    ERROR_DIALOG,
    GAME_OVER_DIALOG
}

enum class ButtonType {
    POSITIVE,
    NEGATIVE
}
