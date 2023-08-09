package com.sukajee.wordle.ui.screens.mainscreen

data class KeyState(
    val redKeyList: MutableSet<Char> = mutableSetOf(),
    val orangeKeyList: MutableSet<Char> = mutableSetOf(),
    val greenKeyList: MutableSet<Char> = mutableSetOf()
)
