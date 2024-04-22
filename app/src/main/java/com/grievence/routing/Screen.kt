package com.grievence.routing

sealed class Screen(val route: String) {

    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object MainScreen: Screen("main_screen")
    object Detail: Screen("detail_screen")
    object RatingScreen: Screen("rating_screen")
    object GrievenceScreen: Screen("grievence_screen")


}