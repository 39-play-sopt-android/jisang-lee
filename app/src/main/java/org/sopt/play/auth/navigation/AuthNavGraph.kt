package org.sopt.play.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.sopt.play.auth.data.AuthRepository
import org.sopt.play.auth.presentation.LoginRoute
import org.sopt.play.auth.presentation.RegisterRoute
import org.sopt.play.navigation.AppRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    repository: AuthRepository,
    onLogin: () -> Unit
) {
    navigation<AppRoute.Auth>(
        startDestination = AppRoute.Login
    ) {
        composable<AppRoute.Login> {
            LoginRoute(
                repository = repository,
                onLogin = onLogin,
                onSignUp = {
                    navController.navigate(AppRoute.Register) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AppRoute.Register> {
            RegisterRoute(
                repository = repository,
                onRegister = {
                    navController.popBackStack<AppRoute.Login>(
                        inclusive = false
                    )
                }
            )
        }
    }
}
