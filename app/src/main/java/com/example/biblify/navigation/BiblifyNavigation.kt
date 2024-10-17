package com.example.biblify.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.biblify.screens.detail.DetailsScreen
import com.example.biblify.screens.home.HomeScreen
import com.example.biblify.screens.login.LoginScreen
import com.example.biblify.screens.register.CreateAccountScreen
import com.example.biblify.screens.search.SearchScreen
import com.example.biblify.screens.search.SearchViewModel
import com.example.biblify.screens.splash.SplashScreen
import com.example.biblify.screens.stats.StatsScreen
import com.example.biblify.screens.update.UpdateScreen

@Composable
fun BiblifyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BiblifyScreens.SplashScreen.name) {
        composable(BiblifyScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(BiblifyScreens.CreateAccountScreen.name) {
            CreateAccountScreen(navController = navController)
        }
        composable(BiblifyScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        val route = BiblifyScreens.DetailsScreen.name
        composable(
            route = "$route/{bookID}",
            arguments = listOf(
                navArgument(name = "bookID") {
                    type = NavType.StringType
                }
            )
        ) {backStackEntry->
            backStackEntry.arguments?.getString("bookID").let {
                DetailsScreen(navController = navController, bookID = it.toString())
            }

        }
        composable(BiblifyScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(BiblifyScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController, searchViewModel)
        }
        composable(BiblifyScreens.StatsScreen.name) {
            StatsScreen(navController = navController)
        }
        composable(BiblifyScreens.UpdateScreen.name) {
            UpdateScreen(navController = navController)
        }
    }
}