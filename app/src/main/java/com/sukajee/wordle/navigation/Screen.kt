package com.sukajee.wordle.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen(route = "home-screen")
    object StatsScreen: Screen(route = "stats-screen")
    object SettingsScreen: Screen(route = "settings-screen")
    object HelpScreen: Screen(route = "help-screen")
}
