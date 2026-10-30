package org.sopt.play.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.sopt.play.auth.presentation.LoginScreen
import org.sopt.play.auth.presentation.RegisterScreen
import org.sopt.play.navigation.AppRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLogin: (String, String) -> Unit,
    onRegister: (String, String, String) -> Unit
) {
    navigation<AppRoute.Auth>(
        startDestination = AppRoute.Login
    ) {
        composable<AppRoute.Login> {
            LoginScreen(
                onLogin = onLogin,
                onSignUp = {
                    navController.navigate(AppRoute.Register) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AppRoute.Register> {
            RegisterScreen(
                onRegister = onRegister
            )
        }
    }
}