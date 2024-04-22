package com.grievence.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grievence.ui.detail.DetailScreen
import com.grievence.ui.grievence.GrievenceScreen
import com.grievence.ui.login.LoginScreen
import com.grievence.ui.main.MainScreen
import com.grievence.ui.rating.RatingScreen
import com.grievence.ui.register.RegisterScreen
import com.grievence.ui.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.Detail.route+ "/{name}"+"/{image}"+"/{address}"+"/{detail}") {
            val name = it.arguments?.getString("name")
            val image = it.arguments?.getString("image").toString().toInt()
            val address = it.arguments?.getString("address")
            val detail = it.arguments?.getString("detail")
            if (name != null) {
                if (image != null) {
                    if (address != null) {
                        if (detail != null) {
                            DetailScreen(
                                navController = navController,
                                name = name,
                                image = image,
                                address = address,
                                detail = detail
                            )
                        }
                    }
                }
            }
        }
        composable(route = Screen.RatingScreen.route) {
            RatingScreen(navController = navController)
        }
        composable(route = Screen.GrievenceScreen.route) {
            GrievenceScreen(navController = navController)
        }
    }

}