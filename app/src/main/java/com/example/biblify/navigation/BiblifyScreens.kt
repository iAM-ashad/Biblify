package com.example.biblify.navigation

enum class BiblifyScreens {
    DetailsScreen,
    HomeScreen,
    LoginScreen,
    CreateAccountScreen,
    SearchScreen,
    SplashScreen,
    StatsScreen,
    UpdateScreen;

    companion object {
        fun fromRoute(route: String?): BiblifyScreens
            = when(route?.substringBefore("/")) {
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                DetailsScreen.name -> DetailsScreen
                HomeScreen.name -> HomeScreen
                CreateAccountScreen.name -> CreateAccountScreen
                SearchScreen.name -> SearchScreen
                StatsScreen.name -> StatsScreen
                UpdateScreen.name -> UpdateScreen
                null -> HomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }
}