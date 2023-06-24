package com.sukajee.wordle.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sukajee.wordle.ui.screens.mainscreen.MainScreen
import com.sukajee.wordle.ui.screens.mainscreen.MainViewModel
import com.sukajee.wordle.ui.screens.statsscreen.StatsScreen
import com.sukajee.wordle.ui.screens.statsscreen.StatsViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            val mainViewModel: MainViewModel = hiltViewModel()
            MainScreen(navController = navController, viewModel = mainViewModel)
        }
        composable(route = Screen.StatsScreen.route) {
            val statsViewModel: StatsViewModel = hiltViewModel()
            StatsScreen(navController = navController, statsViewModel = statsViewModel)
        }
        composable(route = Screen.SettingsScreen.route) {}
        composable(route = Screen.HelpScreen.route) {}
    }
}
