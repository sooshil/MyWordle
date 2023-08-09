package com.sukajee.wordle.ui.screens.statsscreen

data class StatsUiState(
    val playStats: PlayStats = PlayStats(
        playedCount = 0,
        wonCount = 0,
        currentStreak = 0,
        maxStreak = 0
    ),
    val guesses: Map<Int, Int> = emptyMap()
)

data class PlayStats(
    val playedCount: Int,
    val wonCount: Int,
    val currentStreak: Int,
    val maxStreak: Int
)
