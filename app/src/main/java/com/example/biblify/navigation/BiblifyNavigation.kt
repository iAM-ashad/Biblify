package com.example.biblify.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.biblify.screens.detail.DetailsScreen
import com.example.biblify.screens.home.HomeScreen
import com.example.biblify.screens.home.HomeScreenViewModel
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
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController, viewModel)
        }
        val detailsRoute = BiblifyScreens.DetailsScreen.name
        composable(
            route = "$detailsRoute/{bookID}",
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
        val updateRoute = BiblifyScreens.UpdateScreen.name
        composable(
            route = "$updateRoute/{bookItemID}",
            arguments = listOf(
                navArgument(
                    name = "bookItemID") {
                    type = NavType.StringType
                }
            )
        ) {navBackStackEntry->
            navBackStackEntry.arguments?.getString("bookItemID").let { bookItemID->
                UpdateScreen(navController, bookItemID.toString())
            }
        }
    }
}