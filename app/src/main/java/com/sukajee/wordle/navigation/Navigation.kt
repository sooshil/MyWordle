package com.sukajee.wordle.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Naviagation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {}
        composable(route = Screen.StatsScreen.route) {}
        composable(route = Screen.SettingsScreen.route) {}
        composable(route = Screen.HelpScreen.route) {}
    }
}
