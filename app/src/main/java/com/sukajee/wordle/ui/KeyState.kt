package com.sukajee.wordle.ui

data class KeyState (
    val redKeyList: MutableList<Char> = mutableListOf(),
    val orangeKeyList: MutableList<Char> = mutableListOf(),
    val greenKeyList: MutableList<Char> = mutableListOf()
)
